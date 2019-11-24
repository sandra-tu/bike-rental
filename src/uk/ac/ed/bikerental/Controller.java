package uk.ac.ed.bikerental;

import java.util.*;

public class Controller {
    
    private Set<Provider> providers;
    
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
                    if (intersection.isEmpty()) {
                        break;
                    }
                    Bike bikeArray[] = new Bike[intersection.size()];
                    bikeArray = intersection.toArray(bikeArray);
                    Bike matchedBike = bikeArray[0];
                    matchedBikeSet.add(matchedBike);
                    providerStock.remove(matchedBike);
                }
            }
            Quote quote = new Quote(matchedBikeSet, provider, dateRangeRequested, hireLocation);
            quoteList.add(quote);           
        }
        return quoteList;
    }

    //Helper methods for generateQuotes()
    
    //Returns a list of providers that are near to the customer
    public ArrayList<Provider> getProvidersInRange(Location locationRequested) {
        ArrayList<Provider> providersInRange = new ArrayList<>();
        for (Provider provider : providers) {
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
    
    //Returns bikes that are availible for a given date range
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
            //bikeSet.remove(bike); // Do we need this line?
        }
        return bikesAvailible;
    }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
