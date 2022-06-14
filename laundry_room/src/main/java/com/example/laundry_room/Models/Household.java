package com.example.laundry_room.Models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Household {

    @Id
    private Long id;
    private String owner;
    @ManyToOne
    private Building building;

    public Household() {
    }

    public Household(Long id, String owner, Building building) {
        this.id = id;
        this.owner = owner;
        this.building = building;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }
}
