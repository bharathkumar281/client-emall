package com.emall.client.staff;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow.Subscriber;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.emall.client.booking.Booking;

@RestController
@RequestMapping(path = "/staff")
@Transactional
@CrossOrigin
public class StaffService {

	@Autowired
	private StaffRepository staffRepository;

	@GetMapping(path = "/all")
	public Iterable<Staff> getStaff() {
		return staffRepository.findAll();
	}

	@PostMapping(path = "/add")
	public Staff addStaff(@RequestBody Staff newStaff) throws Exception {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest
				.newBuilder()
				.uri(URI.create("http://localhost:8083/admin/is-admin?email=" + newStaff.getEmail()))
				.GET()
				.build();
		
		if(client.send(request, BodyHandlers.ofString()).body().equals("true")) return null;
		
		for(Staff staff: staffRepository.findAll()) {
			if(staff.getEmail().equalsIgnoreCase(newStaff.getEmail())) {
				return null;
			}
		}
		return staffRepository.save(newStaff);
	}

	@GetMapping(path = "/from-mall")
	public List<Staff> findByMall(@RequestParam Integer id) {
		return staffRepository.findByMallId(id);
	}

	@DeleteMapping(path = "/delete-by-mall")
	public String deleteByMallId(@RequestParam Integer id) {
		staffRepository.deleteByMallId(id);
		return "success";
	}

	@PostMapping(path = "/login")
	public Staff loginValidation(@RequestBody Staff user) {

		for (Staff s : staffRepository.findAll()) {
			if (s.getEmail().equals(user.getEmail()) && s.getPassword().equals(user.getPassword())) {
				return s;
			}
		}
		return null;
	}
	
	@GetMapping(path = "/get")
	public Staff getFromId(@RequestParam Integer id) {
		return this.staffRepository.findById(id).get();
	}
	
	@GetMapping(path = "/is-staff")
	public String isStaff(@RequestParam String email) {
		return staffRepository.existsByEmail(email) ? "true": "false";
	}
}
