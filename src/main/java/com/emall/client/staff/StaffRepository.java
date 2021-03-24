package com.emall.client.staff;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.emall.client.staff.Staff;

@Repository
public interface StaffRepository extends CrudRepository<Staff, Integer> {
	public List<Staff> findByMallId(Integer mallId);
	public void deleteByMallId(Integer mallId);
}
