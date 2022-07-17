package com.maxxton.ticketmanagent.ticketmanagement_parent.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.maxxton.ticketmanagent.ticketmanagement_parent.model.Users;
import com.maxxton.ticketmanagent.ticketmanagement_parent.service.UserService;

@RestController
public class UserController {

	@Autowired
	UserService userService;

	@RequestMapping(value = "/user/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Users> createUser(@RequestBody Users user) {
		Users response = userService.createUsers(user);
		return new ResponseEntity<Users>(response, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/user/findAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Users>> findAllUsers() {

		List<Users> response = userService.findAllUsers();
		return new ResponseEntity<List<Users>>(response, HttpStatus.OK);

	}

	@RequestMapping(value = "/user/delete", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Users> deletUserById(@RequestBody Users user) {
		Users response = userService.deletUserById(user);
		return new ResponseEntity<Users>(response, HttpStatus.ACCEPTED);

	}
	
	@RequestMapping(value = "/user/findById/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Users> findUserById(@PathVariable long id) {
		Users response = userService.findUserById(id);
		return new ResponseEntity<Users>(response, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/user/deleteAll", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteAllUser() {
		userService.deleteAllUser();
		return new ResponseEntity<>("Deleted Successfully", HttpStatus.ACCEPTED);

	}
	
	@RequestMapping(value = "/user/update/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Users> updateEmployeeById(@RequestBody Users user, @PathVariable long id) {
		Users response = userService.updateUserById(user , id);
		return new ResponseEntity<Users>(response, HttpStatus.OK);

	}
	

}
