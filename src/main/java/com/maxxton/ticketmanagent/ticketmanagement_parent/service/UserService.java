package com.maxxton.ticketmanagent.ticketmanagement_parent.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maxxton.ticketmanagent.ticketmanagement_parent.exceptions.InvalidIdException;
import com.maxxton.ticketmanagent.ticketmanagement_parent.exceptions.ItemNotFoundException;
import com.maxxton.ticketmanagent.ticketmanagement_parent.exceptions.PasswordMismatchEception;
import com.maxxton.ticketmanagent.ticketmanagement_parent.model.Employee;
import com.maxxton.ticketmanagent.ticketmanagement_parent.model.Users;
import com.maxxton.ticketmanagent.ticketmanagement_parent.repo.UserRepo;

@Transactional
@Service
public class UserService {

	@Autowired
	UserRepo userRepo;

	Logger logger = LoggerFactory.getLogger(UserService.class);

	public Users createUsers(Users user) {
		Users userDetails = userRepo.findByEmployeeId(user.getEmployee().getId());
		if (null != userDetails) {
			throw new InvalidIdException("Employee id already exists");
		}
		Users response = userRepo.save(user);
		logger.info("User created successfully");
		return response;
	}

	public List<Users> findAllUsers() {
		Iterable<Users> userEntity = userRepo.findAll();
		List<Users> userList = new ArrayList<>();
		if (null == userEntity) {
			throw new ItemNotFoundException("User Details Not Found");
		} else {
			for (Users us : userEntity) {
				Users user = new Users();
				user.setId(us.getId());
				user.setUsername(us.getUsername());
				user.setPassword(us.getPassword());
				Employee employee = new Employee();
				employee.setId(us.getEmployee().getId());
				employee.setDesignation(us.getEmployee().getDesignation());
				employee.setName(us.getEmployee().getName());
				user.setEmployee(employee);
				userList.add(user);
			}
			logger.info("Users Found Successfully");
			return userList;
		}
	}

	public Long deletUserById(long id) throws Exception {
		try {
			userRepo.deleteById(id);
			return id;
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}

	public Users findUserById(long id) {

		Optional<Users> user = userRepo.findById(id);
		if (!user.isPresent()) {
			throw new ItemNotFoundException("User Details Not Found");
		} else {
			Users response = user.get();
			return response;
		}
	}

	public void deleteAllUser() throws Exception {
		try {
			userRepo.deleteAll();
		} catch (Exception ex) {
			throw new Exception(ex);
		}

	}

	public Users updateUserById(Users user, long id) {

		Optional<Users> userEntity = userRepo.findById(id);
		if (userEntity.isPresent()) {
			Users userDetails = userRepo.findByEmployeeId(user.getEmployee().getId());
			if (null != userDetails && (userDetails.getEmployee().getId() != user.getEmployee().getId())) {
				throw new InvalidIdException("Employee id Cannot be Modified.");
			}
			Users response = userRepo.save(user);
			return response;
		} else {
			throw new ItemNotFoundException("User not found");
		}
	}

	public Users resetPassword(String oldPassword, long employee_id, String newPassword) {

		Users userEntity = userRepo.findByEmployeeId(employee_id);
		if (null == userEntity) {
			throw new ItemNotFoundException("User not found");
		} else {
			if (userEntity.getPassword().equals(oldPassword)) {
				userEntity.setPassword(newPassword);
				Users response = userRepo.save(userEntity);
				return response;
			} else {
				throw new PasswordMismatchEception("Invalid Old Password");

			}
		}
	}

	public Users forgotPassword(long employee_id, String newPassword) {

		Users userEntity = userRepo.findByEmployeeId(employee_id);
		if (null == userEntity) {
			throw new ItemNotFoundException("User not found");
		} else {
			userEntity.setPassword(newPassword);
			Users response = userRepo.save(userEntity);
			return response;
		}
	}

	public Users findUserByEmployeeId(long emp_id) {
		Users userEntity = userRepo.findByEmployeeId(emp_id);
		if (null == userEntity) {
			throw new ItemNotFoundException("User not found");
		} else {
			return userEntity;
		}
	}

}
