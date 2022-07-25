package com.maxxton.ticketmanagent.ticketmanagement_parent.repo;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.maxxton.ticketmanagent.ticketmanagement_parent.model.TicketAssignTo;

@Repository
public interface TicketAssignToRepo extends JpaRepository<TicketAssignTo, Long> {

	@Query( value = "SELECT * FROM t_ticket_assign_to WHERE my_emp_id =  ?1", nativeQuery = true)
	Set<TicketAssignTo> findByTicketAssignTo_Employee(long emp_id);
}

