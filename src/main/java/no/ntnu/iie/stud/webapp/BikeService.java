package no.ntnu.iie.stud.webapp;

import no.ntnu.iie.stud.webapp.entities.Bike;
import no.ntnu.iie.stud.webapp.entities.Booking;
import no.ntnu.iie.stud.webapp.entities.ParkingSpot;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.HashMap;

/**
 * Created by Audun on 15.09.2016.
 */
@Path("/")
public class BikeService {
    private static HashMap<Integer, ParkingSpot> parkingSpots;
    static {
        parkingSpots = new HashMap<>();
        parkingSpots.put(1, new ParkingSpot(1, "Dragvoll"));
        parkingSpots.put(2, new ParkingSpot(2, "Gløshaugen"));
        parkingSpots.put(3, new ParkingSpot(3, "Kalvskinnet"));
        parkingSpots.put(4, new ParkingSpot(4, "Midtbyen"));
        parkingSpots.put(5, new ParkingSpot(5, "Solsiden"));
        parkingSpots.put(6, new ParkingSpot(6, "Malvik"));

        parkingSpots.get(1).addBike(new Bike(1, 0.50, true));
        parkingSpots.get(1).addBike(new Bike(2, 0.75, true));
        parkingSpots.get(1).addBike(new Bike(37, 0.72, true));
        parkingSpots.get(1).addBike(new Bike(102, 0.18, true));
        parkingSpots.get(1).addBike(new Bike(38, 0.10, true));
        parkingSpots.get(1).addBike(new Bike(84, 1.0, true));
        parkingSpots.get(1).addBike(new Bike(6, 1.0, true));

        parkingSpots.get(2).addBike(new Bike(28, 1.0, true));
        parkingSpots.get(2).addBike(new Bike(8, 0.50, true));
        parkingSpots.get(2).addBike(new Bike(69, 0.75, true));
        parkingSpots.get(2).addBike(new Bike(39, 0.72, true));
        parkingSpots.get(2).addBike(new Bike(102, 0.18, true));

        parkingSpots.get(3).addBike(new Bike(10, 1.0, true));
        parkingSpots.get(3).addBike(new Bike(116, 0.20, true));
        parkingSpots.get(3).addBike(new Bike(22, 0.88, true));
        parkingSpots.get(3).addBike(new Bike(30, 0.70, true));

        parkingSpots.get(4).addBike(new Bike(68, 0.27, true));
        parkingSpots.get(4).addBike(new Bike(119, 0.64, true));
        parkingSpots.get(4).addBike(new Bike(3, 0.40, true));

        parkingSpots.get(5).addBike(new Bike(51, 0.90, true));


    }

    @GET
    @Path("/parkingspots/{spotId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ParkingSpot getParkingSpot(@PathParam("spotId") int spotId) {
        if(!parkingSpots.containsKey(spotId)) throw new NotFoundException();

        return parkingSpots.get(spotId);
    }

    @GET
    @Path("/parkingspots")
    public Collection<ParkingSpot> getParkingSpots() {
        return parkingSpots.values();
    }

    @POST
    @Path("/parkingspots/{spotId}/book")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addBooking(@PathParam("spotId") int spotId, @FormParam("bikeId") int bikeId) {
        if(!parkingSpots.containsKey(spotId)) throw new NotFoundException();

        ParkingSpot spot = parkingSpots.get(spotId);
        Booking booking = spot.createBooking("root", bikeId);
        if(booking == null) return Response.status(Response.Status.FORBIDDEN).build();

        return Response.ok(booking, MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Path("/bikes")
    public Response changeBikeState(@FormParam("bikeId") int bikeId, @FormParam("spotId") int spotId, @FormParam("code") String code) {
        if(!parkingSpots.containsKey(spotId)) throw new NotFoundException();

        ParkingSpot spot = parkingSpots.get(spotId);
        if(!spot.getBikes().containsKey(bikeId)) throw new NotFoundException();
        Bike bike = spot.getBikes().get(bikeId);

        // Check if booked. If booked, check the code
        if(spot.hasBooking(bikeId)) {
            Booking booking = spot.getBooking(bikeId);
            if(!booking.getBookingCode().equalsIgnoreCase(code)) return Response.status(Response.Status.FORBIDDEN).build();

            spot.removeBooking(bikeId);
            return Response.ok(bike, MediaType.APPLICATION_JSON).build();
        }

        // User wants to return bike?
        if(bike.isAvailable()) return Response.status(Response.Status.FORBIDDEN).build();

        bike.setAvailable(true);
        return Response.ok(bike, MediaType.APPLICATION_JSON).build();
    }
}
