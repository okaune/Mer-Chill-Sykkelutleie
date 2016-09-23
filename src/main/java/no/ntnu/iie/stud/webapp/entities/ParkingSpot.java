package no.ntnu.iie.stud.webapp.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by Audun on 15.09.2016.
 */
public class ParkingSpot {
    private int id;
    private String locationName;
    private int capacity;
    private HashMap<Integer, Bike> bikes;
    private HashMap<Integer, Booking> bookings;

    public ParkingSpot() {

    }

    public ParkingSpot(int id, String locationName, int capacity) {
        this.id = id;
        this.locationName = locationName;
        this.capacity = capacity;
        this.bikes = new HashMap<>();
        this.bookings = new HashMap<>();
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public HashMap<Integer, Bike> getBikes() {
        return bikes;
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

    public Booking createBooking(String username, int bikeId) {
        if(bookings.containsKey(bikeId)) return null;
        if(!bikes.containsKey(bikeId)) return null;
        Bike bike = bikes.get(bikeId);
        Booking booking = new Booking(bike, username, generateBookingCode());
        bookings.put(bikeId, booking);
        bike.setAvailable(false);
        return booking;
    }

    private static Random random = new Random();

    private static String generateBookingCode() {
        return (random.nextInt(899999) + 100000) + "";
    }

    public boolean hasBooking(int bikeId) {
        if(!bookings.containsKey(bikeId)) return false;
        Booking booking = bookings.get(bikeId);

        if(ChronoUnit.MINUTES.between(LocalDateTime.now(), booking.getCreateTime()) > 30) {
            // Time's up!
            bookings.remove(bikeId);

            // Reset bike state
            Bike bike = bikes.get(bikeId);
            bike.setAvailable(true);
            return false;
        }
        return true;
    }

    public Booking getBooking(int bikeId) {
        return bookings.get(bikeId);
    }

    public void removeBooking(int bikeId) {
        bookings.remove(bikeId);
    }
}
