package com.maxxton.ticketmanagent.ticketmanagement_parent.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxxton.ticketmanagent.ticketmanagement_parent.exceptions.ItemNotFoundException;
import com.maxxton.ticketmanagent.ticketmanagement_parent.model.Users;
import com.maxxton.ticketmanagent.ticketmanagement_parent.repo.UserRepo;

@Service
public class UserService {

	@Autowired
	UserRepo userRepo;

	public Users createUsers(Users user) {

		Users response = userRepo.save(user);
		return response;
	}

	public List<Users> findAllUsers() {
		Iterable<Users> response = userRepo.findAll();
		List<Users> userList = new ArrayList<>();
		for (Users us : response) {
			Users u = new Users();
			u.setEmp_id(us.getEmp_id());
			u.setUsername(us.getUsername());
			u.setPassword(u.getPassword());
			userList.add(u);
		}
		return userList;
	}

	public Users deletUserById(Users user) {
		userRepo.delete(user);
		return null;
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
		if(userEntity.isPresent()) {
			
			Users response = userRepo.save(user);
			return response;
		}
		else
		{
			throw new ItemNotFoundException("Item not found");
		}
	}	
	
	
	
	

}
