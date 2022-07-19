package com.maxxton.ticketmanagent.ticketmanagement_parent.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.maxxton.ticketmanagent.ticketmanagement_parent.model.Users;

@Repository
public interface UserRepo extends JpaRepository<Users, Long> {

	@Query( value = "SELECT * FROM T_Users WHERE my_emp_id =  ?1", nativeQuery = true)
	Users findByEmployeeId(long employee_id);

}
