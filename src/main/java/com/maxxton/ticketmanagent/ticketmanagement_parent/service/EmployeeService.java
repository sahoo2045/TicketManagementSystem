package com.maxxton.ticketmanagent.ticketmanagement_parent.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.maxxton.ticketmanagent.ticketmanagement_parent.exceptions.ItemNotFoundException;
import com.maxxton.ticketmanagent.ticketmanagement_parent.model.Employee;
import com.maxxton.ticketmanagent.ticketmanagement_parent.model.Users;
import com.maxxton.ticketmanagent.ticketmanagement_parent.repo.EmployeeRepo;
import com.maxxton.ticketmanagent.ticketmanagement_parent.repo.UserRepo;

@Service
public class EmployeeService {

	@Autowired
	EmployeeRepo employeeRepo;

	@Autowired
	UserRepo userRepo;

	Logger logger = LoggerFactory.getLogger(UserService.class);

	public Employee createEmployee(Employee emp) {

		emp.setId(ThreadLocalRandom.current().nextInt(100000, 1000000));
		Employee response = employeeRepo.save(emp);

		Users userEntity = new Users();
		userEntity.setEmployee(response);
		userEntity.setUsername(emp.getName() + emp.getId() + "#");
		userEntity.setPassword(emp.getName() + ThreadLocalRandom.current().nextInt(100000, 1000000));

		userRepo.save(userEntity);
		logger.info("Employee created successfully with user credentials...");

		return response;
	}

	public List<Employee> findAllEmployees() {

		Iterable<Employee> response = employeeRepo.findAll();
		List<Employee> empList = new ArrayList<>();
		if (null == response) {
			throw new ItemNotFoundException("Employee details not found");
		} else {
			for (Employee em : response) {
				Employee m = new Employee();
				m.setId(em.getId());
				m.setName(em.getName());
				m.setDesignation(em.getDesignation());
				empList.add(m);
			}
			logger.info("List of all Employees");
		}
		return empList;
	}

	public long deleteEmployeeById(long id) throws Exception {

		try {
			Users user = userRepo.findByEmployeeId(id);
			if (null != user) {
				userRepo.delete(user);
				employeeRepo.deleteById(id);
				logger.info("Employee Deleted");
				return id;
			} else {
				employeeRepo.deleteById(id);
				return id;
			}

		} catch (DataIntegrityViolationException exception) {
			logger.error("Data Integrity Violation");
			throw new DataIntegrityViolationException("Kindly delete any reference related to this employee");
		} catch (ItemNotFoundException ex) {
			logger.error("Employee not found");
			throw new ItemNotFoundException("Data Unavailable", ex);
		} catch (Exception ex) {
			throw new Exception("Unexpected exception occured", ex);
		}

	}

	public Employee findEmployeeById(Long id) {

		Optional<Employee> emp_id = employeeRepo.findById(id);
		if (!emp_id.isPresent()) {
			throw new ItemNotFoundException("Employee details not found");
		} else {
			Employee response = emp_id.get();
			logger.info("Employee found");
			return response;
		}
	}

	public Employee updateEmployeeById(Employee emp, long id) {

		Optional<Employee> empEntity = employeeRepo.findById(id);
		if (empEntity.isPresent()) {
			Employee response = employeeRepo.save(emp);
			logger.info("Employee updated successfully");
			return response;
		} else {
			throw new ItemNotFoundException("Employee not found");
		}

	}

	public void deleteAllEmployee() throws Exception {

		try {
			employeeRepo.deleteAll();
			logger.info("All employees deleted...");
		} catch (DataIntegrityViolationException exception) {
			logger.error("Data Integrity Violation");
			throw new DataIntegrityViolationException("Kindly delete any reference available related to all employees");
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}
}