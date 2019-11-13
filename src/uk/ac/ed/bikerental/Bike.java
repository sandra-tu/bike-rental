package uk.ac.ed.bikerental;
import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicLong;

public class Bike {

  private String id;
  private Provider provider;
  private BikeType bikeType;
  private BigDecimal fullReplaceVal;
  private BigDecimal dailyRentalPrice;
  //private BikeStatuses bikeStatus;
  //private Collection<Booking> bookings;
  private static AtomicLong idCounter = new AtomicLong();

  public Bike(Provider provider, BikeType bikeType, BigDecimal fullReplaceVal) {
    String id = createBikeID();
    this.provider = provider.getProviderID();
    this.bikeType = bikeType;
    this.fullReplaceVal = fullReplaceVal;
    HashMap<BikeType, BigDecimal> providerDailyPrices = provider.getDailyRentalPrice();
    this.dailyRentalPrice = providerDailyPrices.get(bikeType);
  }

  public int getProviderID() {
    return this.provider.getProviderID();
  }

  public String createBikeID() {
    return String.valueOf(idCounter.getAndIncrement());
  }

    public BikeType getType() {
        // TODO: Implement Bike.getType
        return this.bikeType;
    }
}
