package no.ntnu.iie.stud.webapp.entities;

import no.ntnu.iie.stud.webapp.data.DataContainer;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Created by Audun on 15.09.2016.
 */
public class Bike {
    private int id;
    private double batteryPercentage;
    private boolean available;
    private int parkingSpotId;

    public Bike() {

    }

    public Bike(int id, double batteryPercentage, boolean available, int parkingSpotId) {
        this.id = id;
        this.batteryPercentage = batteryPercentage;
        this.available = available;
        this.parkingSpotId = parkingSpotId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getBatteryPercentage() {
        return batteryPercentage;
    }

    public void setBatteryPercentage(double batteryPercentage) {
        this.batteryPercentage = batteryPercentage;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @XmlTransient
    public ParkingSpot getParkingSpot() {
        return DataContainer.getParkingSpot(parkingSpotId);
    }

    @XmlTransient
    public void setParkingSpot(ParkingSpot parkingSpot) {
        parkingSpotId = parkingSpot.getId();
    }
}
