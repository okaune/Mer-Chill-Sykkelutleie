package no.ntnu.iie.stud.webapp.data;

import no.ntnu.iie.stud.webapp.entities.Bike;
import no.ntnu.iie.stud.webapp.entities.ParkingSpot;

import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Audun on 23.09.2016.
 */
public class DataContainer {
    private static HashMap<Integer, ParkingSpot> parkingSpots = new HashMap<>();
    private static HashMap<Integer, Bike> bikes = new HashMap<>();
    static {
        addParkingSpot(new ParkingSpot(1, "Dragvoll", 10));
        addParkingSpot(new ParkingSpot(2, "Gløshaugen", 7));
        addParkingSpot(new ParkingSpot(3, "Kalvskinnet", 7));
        addParkingSpot(new ParkingSpot(4, "Midtbyen", 10));
        addParkingSpot(new ParkingSpot(5, "Solsiden", 11));
        addParkingSpot(new ParkingSpot(6, "Malvik", 3));


        addBike(new Bike(1, 0.50, true, 1));
        addBike(new Bike(2, 0.75, true, 1));
        addBike(new Bike(37, 0.72, true, 1));
        addBike(new Bike(102, 0.18, true, 1));
        addBike(new Bike(38, 0.10, true, 2));
        addBike(new Bike(84, 1.0, true, 2));
        addBike(new Bike(6, 1.0, true, 2));
        addBike(new Bike(3, 0, false, 3));
        addBike(new Bike(5, 0, false, -1));
    }

    public static Bike getBike(int bikeId) {
        return bikes.get(bikeId);
    }

    public static Bike addBike(Bike bike) {
        if(bikes.containsKey(bike.getId())) return null;
        bikes.put(bike.getId(), bike);

        // Add to parking spot
        ParkingSpot parkingSpot = bike.getParkingSpot();
        if(parkingSpot != null) {
            parkingSpot.addBike(bike);
        }
        return bike;
    }

    public static Collection<ParkingSpot> getParkingSpots() {
        return parkingSpots.values();
    }

    public static ParkingSpot getParkingSpot(int parkingSpotId) {
        return parkingSpots.get(parkingSpotId);
    }

    public static ParkingSpot addParkingSpot(ParkingSpot parkingSpot) {
        return parkingSpots.put(parkingSpot.getId(), parkingSpot);
    }
}
