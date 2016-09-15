package no.ntnu.iie.stud.webapp.entities;

/**
 * Created by Audun on 15.09.2016.
 */
public class Booking {
    private final int id;
    private Bike bike;
    private User user;

    public Booking(int id, Bike bike, User user) {
        this.id = id;
        this.bike = bike;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
