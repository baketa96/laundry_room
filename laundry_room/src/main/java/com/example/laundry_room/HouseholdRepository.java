package com.example.laundry_room;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HouseholdRepository extends CrudRepository<Household, Long> {

    List<Household> findByBuildingId(Long buildingId);

}
