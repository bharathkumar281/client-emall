package com.emall.client.staff;

import java.util.List;

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
	public Staff addStaff(@RequestBody Staff newStaff) {
		for(Staff staff: staffRepository.findAll()) {
			if(staff.getEmail().equals(newStaff.getEmail())) return null;
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
		return "deleted successfully";
	}
	
	@PostMapping(path="/login")
	public Staff loginValidation(@RequestBody Staff user)
	{
	    	  
	    for(Staff s:staffRepository.findAll())
	 {
	    if(s.getEmail().equals(user.getEmail()) && s.getPassword().equals(user.getPassword()))
	 {
	    return s;
	 }
	 }
	return null;
	}
}
