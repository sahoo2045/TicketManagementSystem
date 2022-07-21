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

import com.maxxton.ticketmanagent.ticketmanagement_parent.model.Ticket;
import com.maxxton.ticketmanagent.ticketmanagement_parent.service.TicketService;

@RestController
public class TicketController {

	@Autowired
	TicketService ticketService;

	@RequestMapping(value = "/ticket/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Ticket> createTicket(@RequestBody Ticket tkt) {
		
		Ticket response = ticketService.createTicket(tkt);
		return new ResponseEntity<Ticket>(response, HttpStatus.CREATED);

	}
	
	@RequestMapping(value = "/ticket/findById/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Ticket> findTicketById(@PathVariable long id) {
		
		Ticket response = ticketService.findTicketById(id);
		return new ResponseEntity<Ticket>(response, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/ticket/findAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Ticket>> findAllTickets() {

		List<Ticket> response = ticketService.findAllTickets();
		return new ResponseEntity<List<Ticket>>(response, HttpStatus.OK);

	}
	
	@RequestMapping(value = "/ticket/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> deleteTicketById(@PathVariable long id) {
		
		Long response = ticketService.deleteTicketById(id);
		return new ResponseEntity<Long>(response, HttpStatus.ACCEPTED);

	}
	
	@RequestMapping(value = "/ticket/update/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Ticket> updateTicketById(@RequestBody Ticket tkt, @PathVariable long id) {
		
		Ticket response = ticketService.updateTicketById(tkt , id);
		return new ResponseEntity<Ticket>(response, HttpStatus.OK);

	}

	@RequestMapping(value = "/ticket/deleteAll", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteAllTicket() {
		
		ticketService.deleteAllEmployee();
		return new ResponseEntity<>("Deleted Successfully", HttpStatus.ACCEPTED);

	}
}
