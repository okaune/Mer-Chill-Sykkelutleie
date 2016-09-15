package no.ntnu.iie.stud.webapp.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Audun on 15.09.2016.
 */
public class ParkingSpot {
    private int id;
    private String locationName;
    private HashMap<Integer, Bike> bikes;

    public ParkingSpot() {

    }

    public ParkingSpot(int id, String locationName) {
        this.id = id;
        this.locationName = locationName;
        this.bikes = new HashMap<Integer, Bike>();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Collection<Bike> getAvailableBikes() {
        Collection<Bike> availableBikes = new ArrayList<Bike>();
        for (Bike bike : bikes.values()) {
            if(bike.isAvailable())
                availableBikes.add(bike);
        }
        return availableBikes;
    }

    public void setAvailableBikes(Collection<Bike> bikes) {

    }

    public int getUnavailableBikeCount() {
        return bikes.size() - getAvailableBikes().size();
    }

    public void setUnavailableBikeCount(int count) {

    }

    public void addBike(Bike bike) {
        bikes.put(bike.getId(), bike);
    }
}
