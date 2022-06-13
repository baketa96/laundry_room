package com.example.laundry_room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LaundryRoomController {

    @Autowired
    private LaundryRoomService laundryRoomService;

    @GetMapping("/")
    public String hello(){
        return "Hello Laundry room";
    }

    @GetMapping("/laundry/buildings")
    public ResponseEntity getAllBuildings(){
        return ResponseEntity.status(HttpStatus.OK).body(laundryRoomService.getAllBuildings());
    }


    @GetMapping("/laundry/buildings/{id}/households")
    public ResponseEntity getAllHouseholdsInBuilding(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(laundryRoomService.getHouseholdsInBuilding(id));
    }


    @GetMapping("/laundry/buildings/{id}/rooms")
    public ResponseEntity getLaundryRoomsInBuilding(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(laundryRoomService.getLaundryRoomsInBuilding(id));
    }

    @PostMapping("laundry/buildings")
    public ResponseEntity createBuilding(@RequestBody Building building){
        return ResponseEntity.status(HttpStatus.CREATED).body(laundryRoomService.createBuilding(building));
    }


    @PostMapping("laundry/buildings/{id}/households")
    public ResponseEntity createHousehold(@PathVariable Long id, @RequestBody List<Household> households){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(laundryRoomService.creatHouseholds(id, households));
        } catch (ServiceException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }


    @PostMapping("laundry/buildings/{id}/rooms")
    public ResponseEntity createLaundryRooms(@PathVariable Long id, @RequestBody List<LaundryRoom> rooms){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(laundryRoomService.createLaundryRooms(id, rooms));
        } catch (ServiceException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @GetMapping("laundry/buildings/{id}/booked-times")
    public ResponseEntity getBookedSlotsInBuilding(@PathVariable Long id,
                                                       @RequestParam(defaultValue = "7", name = "days") String days){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(laundryRoomService.getBookedTimesInBuilding(id, days));
        } catch (ServiceException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }


    @PostMapping("laundry/laundry-room/{roomId}/household/{huoseholdId}/booked-times")
    public ResponseEntity createBookSlotInBuilding(@PathVariable Long roomId, @PathVariable Long huoseholdId,@RequestBody BookingSlot bookingSlot){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(laundryRoomService.createBookSlot(roomId, huoseholdId ,bookingSlot));
        } catch (ServiceException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }

    @DeleteMapping("laundry/laundry-room/booked-time/{id}")
    public ResponseEntity deleteBookingSlot(@PathVariable Long id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(laundryRoomService.deleteBookingSlot(id));
        } catch (ServiceException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
        }
    }
}
