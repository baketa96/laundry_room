package com.example.laundry_room.Models;

import javax.persistence.*;

@Entity
public class LaundryRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String name;
    @ManyToOne
    private Building building;


    public LaundryRoom(Long id, String name, Building building) {
        this.id = id;
        this.name = name;
        this.building = building;
    }

    public LaundryRoom() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }
}
