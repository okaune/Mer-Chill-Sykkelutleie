package no.ntnu.iie.stud.webapp;

import no.ntnu.iie.stud.webapp.data.DataContainer;
import no.ntnu.iie.stud.webapp.entities.Bike;
import no.ntnu.iie.stud.webapp.entities.Booking;
import no.ntnu.iie.stud.webapp.entities.ParkingSpot;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Collection;

/**
 * Created by Audun on 15.09.2016.
 */
@Path("/")
public class BikeService {
    @GET
    @Path("/parkingspots/{spotId}")
    @Produces(MediaType.APPLICATION_JSON)
    public ParkingSpot getParkingSpot(@PathParam("spotId") int spotId) {
        ParkingSpot parkingSpot = DataContainer.getParkingSpot(spotId);
        if(parkingSpot == null) throw new NotFoundException();

        return parkingSpot;
    }

    @GET
    @Path("/parkingspots")
    public Collection<ParkingSpot> getParkingSpots() {
        return DataContainer.getParkingSpots();
    }

    @POST
    @Path("/parkingspots/{spotId}/book")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addBooking(@PathParam("spotId") int spotId, @FormParam("bikeId") int bikeId) {
        ParkingSpot parkingSpot = DataContainer.getParkingSpot(spotId);
        if(parkingSpot == null) throw new NotFoundException();

        Booking booking = parkingSpot.createBooking("root", bikeId);
        if(booking == null) return Response.status(Response.Status.FORBIDDEN).build();

        return Response.ok(booking, MediaType.APPLICATION_JSON).build();
    }

    @PUT
    @Path("/bikes")
    public Response changeBikeState(@FormParam("bikeId") int bikeId, @FormParam("spotId") int spotId, @FormParam("code") String code) {
        ParkingSpot parkingSpot = DataContainer.getParkingSpot(spotId);
        if(parkingSpot == null) throw new NotFoundException();

        if(!parkingSpot.getParkedBikes().containsKey(bikeId)) throw new NotFoundException();
        Bike bike = parkingSpot.getParkedBikes().get(bikeId);

        // Check if booked. If booked, check the code
        if(parkingSpot.hasBooking(bikeId)) {
            Booking booking = parkingSpot.getBooking(bikeId);
            if(!booking.getBookingCode().equalsIgnoreCase(code)) return Response.status(Response.Status.FORBIDDEN).build();

            // Remove booking and bike from parking spot
            parkingSpot.removeBike(bikeId);
            parkingSpot.removeBooking(bikeId);
            return Response.ok(bike, MediaType.APPLICATION_JSON).build();
        }

        // User wants to return bike?
        if(bike.isAvailable()) return Response.status(Response.Status.FORBIDDEN).build();

        // Return bike and add it to parking spot
        bike.setAvailable(true);
        parkingSpot.addBike(bike);

        return Response.ok(bike, MediaType.APPLICATION_JSON).build();
    }
}
