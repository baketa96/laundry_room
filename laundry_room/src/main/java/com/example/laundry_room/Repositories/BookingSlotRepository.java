package com.example.laundry_room.Repositories;

import com.example.laundry_room.Models.BookingSlot;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface BookingSlotRepository extends CrudRepository<BookingSlot, Long> {

    List<BookingSlot> findByLaundryRoomIdInAndDateBetween(List<Long> ids, Date start, Date end);
    List<BookingSlot> findByLaundryRoomIdAndDate(Long id, Date day);

    List<BookingSlot> findByHouseholdIdAndDateAfter(Long id, Date date);

}
