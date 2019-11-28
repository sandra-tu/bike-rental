package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import com.sun.istack.internal.Nullable;

import uk.ac.ed.bikerental.BikeType.BikeTypes;
import uk.ac.ed.bikerental.Booking.BookingStatuses;

public class Controller {
    
    private Set<Provider> providers = new HashSet<Provider>();
    public void addProvider(Provider p) {this.providers.add(p);}
    public Set<Provider> getProviders() {return this.providers;}
    
    //Use case 1:
    
    public ArrayList<Quote> generateQuotes(Input input) {
        ArrayList<Quote> quoteList = new ArrayList<>();
        DateRange dateRangeRequested = input.getRequestedDateRange();
        ArrayList<BikeType> bikesRequested = input.getBikesRequested();
        Location hireLocation = input.getRequestedLocation();        
        ArrayList<Provider> providersInRange = getProvidersInRange(hireLocation);
        
        for (Provider provider : providersInRange) {
            Set<Bike> providerStock = provider.getProviderStock();
            Set<Bike> matchedBikeSet = new HashSet<>(); //Bikes that match the input requirements
            for (BikeType bikeType : bikesRequested) {
                if (providerHasBikeType(provider, bikeType)) {
                    Set<Bike> bikesOfType = bikesOfType(providerStock, bikeType);
                    Set<Bike> bikesAvailible = bikesAvailibleDateRange(providerStock, dateRangeRequested);
                    Set<Bike> intersection = new HashSet<>(bikesOfType);
                    intersection.retainAll(bikesAvailible);
                    if (intersection.isEmpty()) {break;}
                    Bike bikeArray[] = new Bike[intersection.size()];
                    bikeArray = intersection.toArray(bikeArray);
                    Bike matchedBike = bikeArray[0];
                    matchedBikeSet.add(matchedBike);
                    providerStock.remove(matchedBike);
                }
            }
            if (!matchedBikeSet.isEmpty()) {
                Quote quote = new Quote(matchedBikeSet, provider, dateRangeRequested, hireLocation);
                quoteList.add(quote); 
            }      
        }
        return quoteList;
    }

    //Helper methods for generateQuotes()
    
    //Returns a list of providers that are near to the customer
    public ArrayList<Provider> getProvidersInRange(Location locationRequested) {
        ArrayList<Provider> providersInRange = new ArrayList<>();
        for (Provider provider : this.providers) {
            Location providerLocation = provider.getProviderAddress();
            if (providerLocation.isNearTo(locationRequested)) {
                providersInRange.add(provider);
            }
        }
        return providersInRange;
    }
    
    //Returns a boolean indicating whether a given provider has a certain bike type
    public boolean providerHasBikeType(Provider provider, BikeType bikeType) {
        Set<BikeType> providerBikeTypes = provider.getStockedBikeTypes();
        boolean hasBikeType = providerBikeTypes.contains(bikeType);
        return hasBikeType;
    }
    
    //Returns a subset of the provider's bikes that are of type bikeType given as an argument
    public Set<Bike> bikesOfType(Set<Bike> providerStock, BikeType bikeType) {
        Set<Bike> outputBikes = new HashSet<>();
        for (Bike bike : providerStock) {
            BikeType stockType = bike.getType();
            if (stockType == bikeType) {
                outputBikes.add(bike);
            }
            //providerStock.remove(bike);
        }
        return outputBikes;
    }
    
    //Returns bikes that are available for a given date range
    public Set<Bike> bikesAvailibleDateRange(Set<Bike> bikeSet, DateRange dateRangeRequested) {
        Set<Bike> bikesAvailible = new HashSet<>();
        for (Bike bike : bikeSet) {
            ArrayList<DateRange>  dateRanges = bike.getDateRangesBooked();
            boolean overlaps = false;
            for (DateRange dateRange : dateRanges) {
                if (dateRange.overlaps(dateRangeRequested)) {
                    overlaps = true;
                }
            }
            if (!overlaps) {
                bikesAvailible.add(bike);
            }
        }
        return bikesAvailible;
    }
    
    //Use case 2:
    
    //bookQuote() overloaded for optional delivery and return to partner
    public Invoice bookQuote(Quote quote, boolean deliveryRequired, Location customerLocation,
                             Provider returnProvider) {
        if(quote.getIsPaid()) {
            Booking booking = new Booking(quote, deliveryRequired, customerLocation, returnProvider);
            //booking.getProvider().addProviderBooking(booking);
            Invoice invoice = new Invoice(booking);
            return invoice;
        } else {
            throw new IllegalArgumentException("Payment needs to be made before "
                    + "Booking is created");
        }
    }
    
    //Delivery required but return to original provider
    public Invoice bookQuote(Quote quote, boolean deliveryRequired, Location customerLocation) {
        if(quote.getIsPaid()) {
            Booking booking = new Booking(quote, deliveryRequired, customerLocation);
            //booking.getProvider().addProviderBooking(booking);
            Invoice invoice = new Invoice(booking);
            return invoice;
        } else {
            throw new IllegalArgumentException("Payment needs to be made before Booking is created");
        }
    }
    
    //Delivery not required but return to partner provider
    public Invoice bookQuote(Quote quote, boolean deliveryRequired, Provider returnProvider) {
        if(quote.getIsPaid()) {
            Booking booking = new Booking(quote, deliveryRequired, returnProvider);
            //booking.getProvider().addProviderBooking(booking);
            Invoice invoice = new Invoice(booking);
            return invoice;
        } else {
            throw new IllegalArgumentException("Payment needs to be made before Booking is created");
        }
    }
    
    //Neither is required
    public Invoice bookQuote(Quote quote, boolean deliveryRequired) {
        if(quote.getIsPaid()) {
            Booking booking = new Booking(quote, deliveryRequired);
            //booking.getProvider().addProviderBooking(booking);
            Invoice invoice = new Invoice(booking);
            return invoice;
        } else {
            throw new IllegalArgumentException("Payment needs to be made before Booking is created");
        }
    }
        
    //Use case 3:
    
    public void returnBikesToProvider(Integer bookingNumber) {
        Booking booking = this.findBookingByNumber(bookingNumber);
        Provider mainProvider = booking.getProvider();
        Provider returnProvider = booking.getReturnProvider();
        
        if (mainProvider.equals(returnProvider)) {
            //Booking returned to main provider
            booking.setBookingStatus(BookingStatuses.COMPLETE);
            booking.setDepositReturned();
        } else {
            //Booking returned to partner provider
            booking.setBookingStatus(BookingStatuses.AT_PARTNER);
            booking.setDepositReturned();
            DeliveryService bookingDelivery = DeliveryServiceFactory.getDeliveryService();
            BikeCollectionFromPartnerToMainProv bikeCol = 
                    new BikeCollectionFromPartnerToMainProv(booking);
            Location pickUp = booking.getReturnProvider().getAddress();
            Location dropOff =  booking.getProvider().getAddress();
            LocalDate pickupDate = booking.getBookingDateRange().getEnd();

            bookingDelivery.scheduleDelivery(bikeCol, pickUp, dropOff, pickupDate);
        }   
    }
    
    //Helper methods: 
    //Gets all booking from all the providers in the system
    public Set<Booking> getAllSystemBookings() {
        Set<Booking> allBookings = new HashSet<>();
        for (Provider provider : this.providers) {
            Set<Booking> providerBookings = provider.getProviderBookings();
            allBookings.addAll(providerBookings);
        }
        return allBookings;
    }
    
    //Finds the booking given to order number / booking number / booking ID
    public Booking findBookingByNumber(Integer bookingNumber) {
        Set<Booking> allBookings = this.getAllSystemBookings();
        Booking output = null;
        for (Booking booking : allBookings) {
            Integer orderNum = booking.getOrderNum();
            if (orderNum.equals(bookingNumber)) {
                output = booking;
            }
        }
        if (output == null) {
            throw new NoSuchElementException("Error: No booking found with this booking number");
        }
        return output;
    }    
    
    //Other:
    
    public static void main(String[] args) {
        Location location = new Location("EH165AY", "Holyrood rd.");
        BigDecimal depositRate = new BigDecimal(0.2);
        Provider provider1 = new Provider("Provider1", location, depositRate);
        BikeType mountainBikeType = new BikeType(BikeTypes.MOUNTAINBIKE, new BigDecimal(100.00));
        BikeType roadBikeType = new BikeType(BikeTypes.ROADBIKE, new BigDecimal(110.00));
        Bike bike1 = new Bike(provider1, mountainBikeType);
        Bike bike2 = new Bike(provider1, mountainBikeType);
        Bike bike3 = new Bike(provider1, mountainBikeType);
        Bike bike4 = new Bike(provider1, roadBikeType);
        Bike bike5 = new Bike(provider1, roadBikeType);
        Set<Bike> stock = new HashSet<>();
        stock.add(bike1);
        stock.add(bike2);
        stock.add(bike3);
        stock.add(bike4);
        stock.add(bike5);
        Set<BikeType> requestedBikeTypes = new HashSet<>();
        requestedBikeTypes.add(mountainBikeType);
        //Set<Quotes>
        
        
    }

}
