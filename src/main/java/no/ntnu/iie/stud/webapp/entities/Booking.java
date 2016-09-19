package no.ntnu.iie.stud.webapp.entities;

import java.time.LocalDateTime;

/**
 * Created by Audun on 15.09.2016.
 */
public class Booking {
    private Bike bike;
    private String user;
    private LocalDateTime createTime;
    private String bookingCode;

    public Booking() {

    }

    public Booking(Bike bike, String user, String bookingCode) {
        this.bike = bike;
        this.user = user;
        this.bookingCode = bookingCode;
        this.createTime = LocalDateTime.now();
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getBookingCode() {
        return bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
    }
}
