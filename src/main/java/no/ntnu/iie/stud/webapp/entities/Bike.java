package no.ntnu.iie.stud.webapp.entities;

/**
 * Created by Audun on 15.09.2016.
 */
public class Bike {
    private int id;
    private double batteryPercentage;
    private boolean available;

    public Bike() {

    }

    public Bike(int id, double batteryPercentage, boolean available) {
        this.id = id;
        this.batteryPercentage = batteryPercentage;
        this.available = available;
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
}
