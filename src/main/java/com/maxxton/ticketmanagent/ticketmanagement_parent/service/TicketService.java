package com.maxxton.ticketmanagent.ticketmanagement_parent.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxxton.ticketmanagent.ticketmanagement_parent.exceptions.ItemNotFoundException;
import com.maxxton.ticketmanagent.ticketmanagement_parent.model.Ticket;
import com.maxxton.ticketmanagent.ticketmanagement_parent.repo.TicketRepo;

@Service
public class TicketService {

	@Autowired
	TicketRepo ticketRepo;

	public Ticket createTicket(Ticket tkt) {
		Ticket response = ticketRepo.save(tkt);
		return response;
	}

	public Ticket findTicketById(long id) {

		Optional<Ticket> tkt_id = ticketRepo.findById(id);
		Ticket response = tkt_id.get();
		return response;

	}

	public List<Ticket> findAllTickets() {

		Iterable<Ticket> response = ticketRepo.findAll();
		List<Ticket> tktList = new ArrayList<>();
		for (Ticket ticket : response) {
			Ticket tkt = new Ticket();
			tkt.setTicket_no(ticket.getTicket_no());
			tkt.setTicket_summary(ticket.getTicket_summary());
			tkt.setTicket_description(ticket.getTicket_description());
			tkt.setTicket_assignedTo(ticket.getTicket_assignedTo());
			tkt.setTicket_createdBy(ticket.getTicket_createdBy());
			tkt.setTicket_riskLevel(ticket.getTicket_riskLevel());
			tkt.setTicket_creationDate(ticket.getTicket_creationDate());
			tkt.setTicket_lastUpdate(ticket.getTicket_creationDate());
			tkt.setTicket_endDate(ticket.getTicket_endDate());
			tkt.setTicket_comments(ticket.getTicket_comments());
			tkt.setTicket_status(ticket.getTicket_status());
			tkt.setTicket_type(ticket.getTicket_type());
			tktList.add(tkt);
		}

		return tktList;
		
	}

	public Long deleteTicketById(long id) {

		try{
			ticketRepo.deleteById(id);
			return id;
		}
		catch(Exception ex){
			throw new ItemNotFoundException("Data Unavailable", ex);
		}
		
	}

	public Ticket updateTicketById(Ticket tkt, long id) {
		
		Optional<Ticket> tktEntity = ticketRepo.findById(id);
		if(tktEntity.isPresent()) {
			
			Ticket response = ticketRepo.save(tkt);
			return response;
		}
		else
		{
			throw new ItemNotFoundException("Item not found");
		}
		
	}

	public void deleteAllEmployee() {

		try{
			ticketRepo.deleteAll();
		}
		catch(Exception ex){
			throw new ItemNotFoundException("Data Unavailable", ex);
		}
	}

}
