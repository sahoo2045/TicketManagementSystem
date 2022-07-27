package com.maxxton.ticketmanagent.ticketmanagement_parent.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.maxxton.ticketmanagent.ticketmanagement_parent.exceptions.InvalidIdException;
import com.maxxton.ticketmanagent.ticketmanagement_parent.model.Users;
import com.maxxton.ticketmanagent.ticketmanagement_parent.service.UserService;

@RestController
public class UserController {

	@Autowired
	UserService userService;
	
	Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@RequestMapping(value = "/user/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Users> createUser(@RequestBody Users user) {
		 
		Users response = userService.createUsers(user);
		logger.info("User created successfully");
		return new ResponseEntity<Users>(response, HttpStatus.CREATED);
		
	}
	
	@RequestMapping(value = "/user/update/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Users> updateUser(@RequestBody Users user, @PathVariable long id) {
		if (user.getId() != id) {
			throw new InvalidIdException("Invalid Id OR user_Id");
		}
		Users response = userService.updateUserById(user, id);
		return new ResponseEntity<Users>(response, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/user/resetPassword/{employee_id}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Users> resetPassword(String oldPassword, @PathVariable long employee_id, String newPassword) {
		
		Users response = userService.resetPassword(oldPassword, employee_id, newPassword);
		return new ResponseEntity<Users>(response, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/user/forgotPassword/{employee_id}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Users> forgotPassword(@PathVariable long employee_id, String newPassword) {
		
		Users response = userService.forgotPassword(employee_id, newPassword);
		return new ResponseEntity<Users>(response, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/user/findAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Users>> findAllUsers() {
		
		List<Users> response = userService.findAllUsers();
		return new ResponseEntity<List<Users>>(response, HttpStatus.OK);
		
	}


	@RequestMapping(value = "/user/findById/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Users> findUserById(@PathVariable long id) {
		
		Users response = userService.findUserById(id);
		return new ResponseEntity<Users>(response, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/user/findByEmployeeId/{emp_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Users> findUserByEmployeeId(@PathVariable long emp_id) {
		
		Users response = userService.findUserByEmployeeId(emp_id);
		return new ResponseEntity<Users>(response, HttpStatus.OK);

	}

	@RequestMapping(value = "/user/deleteAll", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteAllUser() throws Exception {
		
		userService.deleteAllUser();
		return new ResponseEntity<>("Deleted Successfully", HttpStatus.ACCEPTED);

	}
	
	@RequestMapping(value = "/user/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> deletUserById(@PathVariable long id) throws Exception {
		
		Long response = userService.deletUserById(id);
		return new ResponseEntity<Long>(response, HttpStatus.ACCEPTED);
		
	}

}
