package com.example.laundry_room;


import com.example.laundry_room.Models.BookingSlot;
import com.example.laundry_room.Models.Building;
import com.example.laundry_room.Models.Household;
import com.example.laundry_room.Models.LaundryRoom;
import com.example.laundry_room.Repositories.BookingSlotRepository;
import com.example.laundry_room.Repositories.BuildingRepository;
import com.example.laundry_room.Repositories.HouseholdRepository;
import com.example.laundry_room.Repositories.LaundryRoomRepository;
import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LaundryRoomService {

    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private HouseholdRepository householdRepository;

    @Autowired
    private LaundryRoomRepository laundryRoomRepository;
    
    @Autowired
    private BookingSlotRepository bookingSlotRepository;

    private final List<Integer> validTime = Arrays.asList(7,10,13,16,19,22);

    public List<Building> getAllBuildings() {
        return new ArrayList<>((Collection) buildingRepository.findAll());

    }

    public List<Household> getHouseholdsInBuilding(Long id) {

        return new ArrayList<>(householdRepository.findByBuildingId(id));

    }

    public List<LaundryRoom> getLaundryRoomsInBuilding(Long id) {

        return new ArrayList<>(laundryRoomRepository.findByBuildingId(id));

    }

    public Building createBuilding(Building building) {
        return buildingRepository.save(building);
    }

    public Boolean creatHouseholds(Long id, List<Household> households) throws ServiceException {
        Building b = getBuildingById(id);
        households.forEach(a -> a.setBuilding(b));
        householdRepository.saveAll(households);

        return true;

    }

    public Boolean createLaundryRooms(Long id, List<LaundryRoom> rooms) throws ServiceException {
        Building b = getBuildingById(id);
        rooms.forEach(a -> a.setBuilding(b));
        laundryRoomRepository.saveAll(rooms);
        return true;
    }

    public List<BookingSlot> getBookedTimesInBuilding(Long id, String days) throws ServiceException {

        int noDays;
        try{
            noDays = Integer.parseInt(days);
        }catch (NumberFormatException e){
            throw new ServiceException("Error processing request", HttpStatus.BAD_REQUEST.value());
        }

        Building b = getBuildingById(id);
        List<LaundryRoom> laundryRooms = getLaundryRoomsInBuilding(b.getId());
        List<Long> laundryRoomsId = laundryRooms.stream().map(LaundryRoom::getId).collect(Collectors.toList());
        LocalDate startDate = new LocalDate();
        LocalDate endDate = startDate.plusDays(noDays);

        return bookingSlotRepository.findByLaundryRoomIdInAndDateBetween(laundryRoomsId, startDate.toDate(), endDate.toDate());

    }

    public BookingSlot createBookSlot(Long roomId, Long householdId, BookingSlot bookingSlot) throws ServiceException {
        LaundryRoom laundryRoom = laundryRoomRepository.findById(roomId).orElse(null);
        if (laundryRoom == null)
            throw new ServiceException("Laundry room doesn't exist.", HttpStatus.BAD_REQUEST.value());

        Household household = householdRepository.findById(householdId).orElse(null);
        if (household == null)
            throw new ServiceException("Household doesn't exist.", HttpStatus.BAD_REQUEST.value());

        bookingSlot.setLaundryRoom(laundryRoom);
        bookingSlot.setHousehold(household);
        List<BookingSlot> bookingSlotsForDay = bookingSlotRepository.findByLaundryRoomIdAndDate(roomId, bookingSlot.getDate());
        List<BookingSlot> bookingSlotsByHousehold = bookingSlotRepository.findByHouseholdIdAndDateAfter(bookingSlot.getHousehold().getId(), new Date());

        if(validateTimeSlot(bookingSlotsForDay,bookingSlotsByHousehold ,bookingSlot)){
            return bookingSlotRepository.save(bookingSlot);
        }
        throw new ServiceException("Could not create a booking time.", HttpStatus.BAD_REQUEST.value());
    }

    private boolean validateTimeSlot(List<BookingSlot> bookingSlotsForDay, List<BookingSlot> bookingSlotsByHousehold, BookingSlot bookingSlot) throws ServiceException {

        if (!bookingSlotsByHousehold.isEmpty())
            throw new ServiceException("You have reserved booking, please cancel it first.", HttpStatus.BAD_REQUEST.value());

        if(bookingSlot.getStartTime() >= (bookingSlot.getEndTime()) ||
        !validTime.contains(bookingSlot.getStartTime()) || !validTime.contains(bookingSlot.getEndTime()))
            throw new ServiceException("Start and End time of booking slot are not OK!", HttpStatus.BAD_REQUEST.value());

        BookingSlot slot = bookingSlotsForDay.stream().filter(a -> a.getStartTime().equals(bookingSlot.getStartTime()) &&
                a.getEndTime().equals(bookingSlot.getEndTime())).findAny().orElse(null);

        if (slot != null)
            throw new ServiceException("Booked time is already occupied, please choose another.", HttpStatus.BAD_REQUEST.value());

        return true;
    }

    private Building getBuildingById(Long id) throws ServiceException {
        Building b = buildingRepository.findById(id).orElse(null);
        if (b == null)
            throw new ServiceException("Building doesn't exist", HttpStatus.BAD_REQUEST.value());

        return b;
    }

    public Boolean deleteBookingSlot(Long id) throws ServiceException{
        try{
            bookingSlotRepository.deleteById(id);
            return true;
        }catch (EmptyResultDataAccessException e){
            throw new ServiceException("Given booking slot doesn't exist", HttpStatus.BAD_REQUEST.value());
        }

    }
}
