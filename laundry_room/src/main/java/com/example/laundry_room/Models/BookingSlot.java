package com.example.laundry_room.Models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class BookingSlot {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @ManyToOne
    private LaundryRoom laundryRoom;
    @ManyToOne
    private Household household;
    private Date date;
    private Integer startTime;
    private Integer endTime;

    public BookingSlot(Long id, LaundryRoom laundryRoom, Household household, Date date, Integer startTime, Integer endTime) {
        this.id = id;
        this.laundryRoom = laundryRoom;
        this.household = household;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public BookingSlot() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LaundryRoom getLaundryRoom() {
        return laundryRoom;
    }

    public void setLaundryRoom(LaundryRoom laundryRoom) {
        this.laundryRoom = laundryRoom;
    }

    public Household getHousehold() {
        return household;
    }

    public void setHousehold(Household household) {
        this.household = household;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }
}
