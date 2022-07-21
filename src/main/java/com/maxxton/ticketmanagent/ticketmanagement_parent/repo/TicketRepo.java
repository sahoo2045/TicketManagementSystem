package com.maxxton.ticketmanagent.ticketmanagement_parent.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.maxxton.ticketmanagent.ticketmanagement_parent.model.Ticket;

@Repository
public interface TicketRepo extends CrudRepository<Ticket, Long> {

	@Query( value = "SELECT * FROM T_Ticket WHERE emp_id =  ?1", nativeQuery = true)
	List<Ticket> findByEmpId(long emp_id);

}
