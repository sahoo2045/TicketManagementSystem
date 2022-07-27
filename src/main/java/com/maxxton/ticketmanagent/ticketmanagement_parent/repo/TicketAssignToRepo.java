package com.maxxton.ticketmanagent.ticketmanagement_parent.repo;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.maxxton.ticketmanagent.ticketmanagement_parent.model.TicketAssignToDao;

@Repository
public interface TicketAssignToRepo extends JpaRepository<TicketAssignToDao, Long> {

	@Query( value = "SELECT * FROM ticket_assigned_to WHERE employee_id =  ?1", nativeQuery = true)
	Set<TicketAssignToDao> findByTicketAssignTo_Employee(long emp_id);

	@Query( value = "SELECT * FROM ticket_assigned_to WHERE ticket_id =  ?1", nativeQuery = true)
	List<TicketAssignToDao> findByTicketId(long ticket_id);

	@Query( value = "Delete from ticket_assigned_to where ticket_id = ?1 and employee_id = ?2;", nativeQuery = true)
	void deleteByTicketAndEmployeeId(long ticket_id, long emp_id);
	
}

