package com.maxxton.ticketmanagent.ticketmanagement_parent.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.maxxton.ticketmanagent.ticketmanagement_parent.exceptions.ItemNotFoundException;
import com.maxxton.ticketmanagent.ticketmanagement_parent.model.Users;
import com.maxxton.ticketmanagent.ticketmanagement_parent.repo.UserRepo;

@Transactional
@Service
public class UserService {

	@Autowired
	UserRepo userRepo;

	Logger logger = LoggerFactory.getLogger(UserService.class);

	public Users createUsers(Users user) {
		Users response = userRepo.save(user);
		return response;
	}

	public List<Users> findAllUsers() {
		Iterable<Users> response = userRepo.findAll();
		List<Users> userList = new ArrayList<>();
		for (Users us : response) {
			Users u = new Users();
			u.setId(us.getId());
			u.setUsername(us.getUsername());
			u.setPassword(u.getPassword());
			userList.add(u);
		}
		return userList;
	}

	public Long deletUserById(long id) {
		try {
			userRepo.deleteById(id);
			return id;
		} catch (Exception ex) {
			throw new ItemNotFoundException("Data Unavailable", ex);
		}
	}

	public Users findUserById(long id) {

		Optional<Users> user_id = userRepo.findById(id);
		Users response = user_id.get();
		return response;
	}

	public Users deleteAllUser() {

		userRepo.deleteAll();
		return null;
	}

	public Users updateUserById(Users user, long id) {

		Optional<Users> userEntity = userRepo.findById(id);
		if (userEntity.isPresent()) {

			Users response = userRepo.save(user);
			return response;
		} else {
			throw new ItemNotFoundException("Item not found");
		}
	}

	public String loginAuthentication(long id, String uname, String pwd) {

		Optional<Users> userEntity = userRepo.findById(id);
		Users user = userEntity.get();

		if (id == user.getId()) {
			if(uname.equals(user.getUsername()) && pwd.equals(user.getPassword())) {
				return "Authentication Successful";
			}
			else {
				return "Authentication Unsuccessful";
			}
		} else {
			return "Authentication Unsuccessful";
		}
	}

	public Users updatePassword(String oldPassword, long employee_id, String newPassword) {

		Users userEntity = userRepo.findByEmployeeId(employee_id);
		if (userEntity.getPassword() == oldPassword) {
			userEntity.setPassword(newPassword);
			Users response = userRepo.save(userEntity);
			return response;
		} else {
			logger.error("Error level log message");
			throw new ItemNotFoundException("Password Doesnt match");

		}

	}

	public Users forgotPassword(long employee_id, String newPassword) {

		Users userEntity = userRepo.findByEmployeeId(employee_id);
		userEntity.setPassword(newPassword);
		Users response = userRepo.save(userEntity);
		return response;

	}

}
