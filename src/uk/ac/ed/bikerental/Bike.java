package uk.ac.ed.bikerental;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

public class Bike {

  private Integer id;
  private Provider provider;
  private BikeType bikeType;
  private BigDecimal fullReplaceVal;
  private BigDecimal dailyRentalPrice;
  //private BikeStatuses bikeStatus;
  //private Collection<Booking> bookings;
  private static AtomicLong idCounter = new AtomicLong();

  public Bike(Provider provider, BikeType bikeType) {
    this.id = createBikeID();
    this.provider = provider;
    this.bikeType = bikeType;
    this.fullReplaceVal = bikeType.getReplacementValue();
    this.dailyRentalPrice = provider.getDailyRentalPrice(bikeType);
  }

  public int getProviderID() {
    return this.provider.getProviderID();
  }

  public Integer createBikeID() {
    return Integer.valueOf(String.valueOf(idCounter.getAndIncrement()));
  }

    public BikeType getType() {
        // TODO: Implement Bike.getType
        return this.bikeType;
    }
}
