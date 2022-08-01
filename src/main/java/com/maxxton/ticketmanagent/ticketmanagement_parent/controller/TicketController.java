package com.maxxton.ticketmanagent.ticketmanagement_parent.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.maxxton.ticketmanagent.ticketmanagement_parent.exceptions.InvalidIdException;
import com.maxxton.ticketmanagent.ticketmanagement_parent.model.Comment;
import com.maxxton.ticketmanagent.ticketmanagement_parent.model.Employee;
import com.maxxton.ticketmanagent.ticketmanagement_parent.model.Ticket;
import com.maxxton.ticketmanagent.ticketmanagement_parent.model.TicketAssignToDao;
import com.maxxton.ticketmanagent.ticketmanagement_parent.model.TicketStatus;
import com.maxxton.ticketmanagent.ticketmanagement_parent.security.UserDetailsImpl;
import com.maxxton.ticketmanagent.ticketmanagement_parent.service.TicketService;

import springfox.documentation.annotations.ApiIgnore;

@RestController
public class TicketController {

	@Autowired
	TicketService ticketService;

	@RequestMapping(value = "/ticket/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Ticket> createTicket(@RequestBody Ticket tkt) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		long loggedInUserId = userDetails.getUser().getEmployee().getId();
		tkt.setCreatedBy(loggedInUserId);
		Ticket response = ticketService.createTicket(tkt);
		return new ResponseEntity<Ticket>(response, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/ticket/findById/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Ticket> findTicketById(@PathVariable long id) {

		Ticket response = ticketService.findTicketById(id);
		return new ResponseEntity<Ticket>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/ticket/findByEmpId/{emp_id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Ticket>> findTicketByEmpId(@PathVariable long emp_id) {

		List<Ticket> response = ticketService.findTicketByEmpId(emp_id);
		return new ResponseEntity<List<Ticket>>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/ticket/findAll", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Ticket>> findAllTickets() {

		List<Ticket> response = ticketService.findAllTickets();
		return new ResponseEntity<List<Ticket>>(response, HttpStatus.OK);
	}

	@ApiIgnore
	@RequestMapping(value = "/ticket/delete/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Long> deleteTicketById(@PathVariable long id) throws Exception {

		Long response = ticketService.deleteTicketById(id);
		return new ResponseEntity<Long>(response, HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "/ticket/update/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Ticket> updateTicketById(@RequestBody Ticket tkt, @PathVariable long id) {
		if (tkt.getTicketId() != id) {
			throw new InvalidIdException("Invalid Id OR Ticket_no");
		}
		Ticket response = ticketService.updateTicketById(tkt, id);
		return new ResponseEntity<Ticket>(response, HttpStatus.OK);
	}

	@ApiIgnore
	@RequestMapping(value = "/ticket/deleteAll", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deleteAllTicket() throws Exception {

		ticketService.deleteAllTicket();
		return new ResponseEntity<>("Deleted Successfully", HttpStatus.ACCEPTED);
	}

	@RequestMapping(value = "/ticket/assignTicketTo/{ticket_id}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TicketAssignToDao> assignTicketTo(@PathVariable long ticket_id, @RequestBody Employee emp) {

		TicketAssignToDao response = ticketService.assignTicketTo(ticket_id, emp);
		return new ResponseEntity<TicketAssignToDao>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/ticket/updateStatus/{ticket_id}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Ticket> updateStatus(@PathVariable long ticket_id, TicketStatus newStatus) {

		Ticket response = ticketService.updateStatus(ticket_id, newStatus);
		return new ResponseEntity<Ticket>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/ticket/updateWorkingHours/{ticket_id}", method = RequestMethod.PATCH, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Ticket> updateWorkingHours(@PathVariable long ticket_id, @RequestBody Employee emp,
			double workHours) {

		Ticket response = ticketService.updateWorkingHours(ticket_id, emp, workHours);
		return new ResponseEntity<Ticket>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/ticket/addComment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Ticket> addComment(@RequestBody @Valid Comment comment) {

		Ticket response = ticketService.saveComment(comment);
		return new ResponseEntity<Ticket>(response, HttpStatus.OK);
	}

}
