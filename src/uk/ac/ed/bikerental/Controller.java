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
            for (BikeType bikeType : bikesRequested) {
                Set<BikeType> stockedBikeTypes = provider.getStockedBikeTypes();
                if (stockedBikeTypes.contains(bikeType)) {
                    
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
    
    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
