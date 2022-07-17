package com.maxxton.ticketmanagent.ticketmanagement_parent.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.maxxton.ticketmanagent.ticketmanagement_parent.model.Users;

@Repository
public interface UserRepo extends CrudRepository<Users, Long> {

}
