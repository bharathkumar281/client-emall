package com.emall.client.booking;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.emall.client.staff.StaffRepository;
import com.emall.client.Constants;

@RestController
@RequestMapping(path = "/booking")
@CrossOrigin
public class BookingService {
	
	@Autowired
	private BookingRepository bookingRepository;
	
	@Autowired
	private StaffRepository staffRepository;
	
	@GetMapping(path = "/all")
	public Iterable<Booking> getBookings() {
		return bookingRepository.findAll();
	}
	
	@PostMapping(path = "/add")
	public Booking addBooking(@RequestBody Booking booking, @RequestParam Integer id) throws Exception {
		return staffRepository.findById(id).map(staff -> {
			booking.setStaff(staff);
			staff.getBookings().add(bookingRepository.save(booking));
			staffRepository.save(staff);
			return booking;
		}).get();
	}
	
	@DeleteMapping(path = "/delete")
	public boolean deleteBooking(@RequestParam Integer id) {
		if(!bookingRepository.existsById(id)) return false;
		bookingRepository.deleteById(id);
		return true;
	}
	
	@GetMapping(path = "/admin-msg")
	public String getFromAdmin() throws Exception {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest
				.newBuilder()
				.GET()
				.uri(URI.create("http://localhost:8080/admin/msg"))
				.build();
		return client.send(request, BodyHandlers.ofString()).body();
	}
	
	@GetMapping(path = "/from-month")
	public List<Booking> getFromMonth(@RequestParam String month) {
		return bookingRepository.findByStartDateStartsWithOrEndDateStartsWith(month, month);
	}
	
	@GetMapping(path = "/from-month-id")
	public List<Booking> getFromMonthAndId(@RequestParam String month, @RequestParam Integer id) {
		List<Booking> bookings = bookingRepository.findByStartDateStartsWithOrEndDateStartsWith(month, month);
		bookings.removeIf(booking -> booking.getSpaceId() != id);
		return bookings;
	}

}
