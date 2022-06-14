package com.example.laundry_room.Repositories;

import com.example.laundry_room.Models.LaundryRoom;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LaundryRoomRepository extends CrudRepository<LaundryRoom, Long> {

     List<LaundryRoom> findByBuildingId(Long buildingId);
}
