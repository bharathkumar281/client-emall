package com.emall.client.booking;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.emall.client.booking.Booking;

@Repository
public interface BookingRepository extends CrudRepository<Booking, Integer> {
	public List<Booking> findByStartDateStartsWithOrEndDateStartsWith(String month1, String month2);
}
