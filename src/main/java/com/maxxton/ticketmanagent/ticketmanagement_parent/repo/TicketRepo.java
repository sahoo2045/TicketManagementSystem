package com.maxxton.ticketmanagent.ticketmanagement_parent.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maxxton.ticketmanagent.ticketmanagement_parent.model.Ticket;

@Repository
public interface TicketRepo extends JpaRepository<Ticket, Long> {

}
