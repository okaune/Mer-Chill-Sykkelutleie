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
        parkingSpots = new HashMap<Integer, ParkingSpot>();
        parkingSpots.put(1, new ParkingSpot(1, "Dragvoll"));
        parkingSpots.put(2, new ParkingSpot(2, "Gløshaugen"));
        parkingSpots.put(3, new ParkingSpot(3, "Kalvskinnet"));
        parkingSpots.put(4, new ParkingSpot(4, "Midtbyen"));
        parkingSpots.put(5, new ParkingSpot(5, "Solsiden"));

        parkingSpots.get(1).addBike(new Bike(1, 0.50, true));
        parkingSpots.get(1).addBike(new Bike(2, 0.75, true));
        parkingSpots.get(1).addBike(new Bike(3, 0, false));
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
}
