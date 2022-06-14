package com.example.laundry_room.Repositories;

import com.example.laundry_room.Models.Household;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface HouseholdRepository extends CrudRepository<Household, Long> {

    List<Household> findByBuildingId(Long buildingId);

}
