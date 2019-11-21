package uk.ac.ed.bikerental;

import java.util.ArrayList;
import java.util.Set;

public class Controller {
    
    private Set<Provider> providers;
    
    public ArrayList<Quote> generateQuotes(Input input) {
        ArrayList<Quote> quoteList = new ArrayList<>();
        DateRange dateRangeRequested = input.getRequestedDateRange();
        ArrayList<BikeType> bikesRequested = input.getBikesRequested();
        Location hireLocation = input.getRequestedLocation();
        ArrayList<Provider> providersInRange = getProvidersInRange(hireLocation);
        
        for (Provider provider : providersInRange) {
            Set<Bike> providerStockCopy = provider.getProviderStock();
            for (BikeType bikeTypeRequested : bikesRequested) {
                Set<BikeType> stockedBikeTypes = provider.getStockedBikeTypes();
                if (stockedBikeTypes.contains(bikeTypeRequested)) {
                //Only continue searching if the provider has bikes of the requested type
                    for (Bike bikeInStock : providerStockCopy) {
                        BikeType providerStockBikeType = bikeInStock.getType();
                        if (bikeTypeRequested == providerStockBikeType) {
                            boolean match;
                            match = isBikeConcurrentWithInput(bikeInStock, dateRangeRequested);
                            if (match == true) {
                                providerStockCopy.remove(bikeInStock);
                            }
                        }
                    }
                }
            }
        }
        return quoteList;
    }

    //Helper method for generateQuotes()
    //Returns a list of providers that are near to the customer
    public ArrayList<Provider> getProvidersInRange(Location locationRequested) {
        ArrayList<Provider> providersInRange = new ArrayList<>();
        for (Provider provider : providersInRange) {
            Location providerLocation = provider.getProviderAddress();
            if (providerLocation.isNearTo(locationRequested)) {
                providersInRange.add(provider);
            }
        }
        return providersInRange;
    }
    
    //Helper method for generateQuotes()
    //Loop through bookings
    public boolean isBikeConcurrentWithInput(Bike bike, DateRange dateRangeRequested) {
        boolean concurrent = true;
        ArrayList<DateRange> bikeBookedDates = bike.getDateRangesBooked();
        while (concurrent) {
            for (DateRange dateRange : bikeBookedDates) {
                boolean overlaps = (dateRange.overlaps(dateRangeRequested));
                concurrent = concurrent && !overlaps;
            }
        }
        return concurrent;
    }
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
