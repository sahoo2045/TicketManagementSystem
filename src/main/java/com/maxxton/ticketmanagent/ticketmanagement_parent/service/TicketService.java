package com.maxxton.ticketmanagent.ticketmanagement_parent.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxxton.ticketmanagent.ticketmanagement_parent.exceptions.ItemNotFoundException;
import com.maxxton.ticketmanagent.ticketmanagement_parent.model.Employee;
import com.maxxton.ticketmanagent.ticketmanagement_parent.model.Ticket;
import com.maxxton.ticketmanagent.ticketmanagement_parent.model.TicketStatus;
import com.maxxton.ticketmanagent.ticketmanagement_parent.repo.TicketRepo;

@Service
public class TicketService {

	@Autowired
	TicketRepo ticketRepo;

	public Ticket createTicket(Ticket tkt) {

		tkt.setTicket_no(ThreadLocalRandom.current().nextInt(1000, 10000));
		Ticket response = ticketRepo.save(tkt);
		return response;
	}

	public Ticket findTicketById(long id) {

		Optional<Ticket> tkt_id = ticketRepo.findById(id);
		Ticket response = tkt_id.get();
		return response;

	}

	public List<Ticket> findAllTickets() {

		Iterable<Ticket> ticketEntity = ticketRepo.findAll();
		List<Ticket> tktList = new ArrayList<>();
		for (Ticket ticket : ticketEntity) {
			Ticket tkt = new Ticket();
			tkt.setTicket_no(ticket.getTicket_no());
			tkt.setTicket_summary(ticket.getTicket_summary());
			tkt.setTicket_description(ticket.getTicket_description());
			tkt.setTicket_createdBy(ticket.getTicket_createdBy());
			tkt.setTicket_riskLevel(ticket.getTicket_riskLevel());
			tkt.setTicket_creationDate(ticket.getTicket_creationDate());
			tkt.setTicket_lastUpdate(ticket.getTicket_creationDate());
			tkt.setTicket_endDate(ticket.getTicket_endDate());
			tkt.setTicket_comments(ticket.getTicket_comments());
			tkt.setTicket_status(ticket.getTicket_status());
			tkt.setTicket_type(ticket.getTicket_type());
			Employee employee = new Employee();
			employee.setEmp_id(ticket.getEmployee().getEmp_id());
			employee.setEmp_designation(ticket.getEmployee().getEmp_designation());
			employee.setEmp_name(ticket.getEmployee().getEmp_name());
			tkt.setEmployee(employee);
			tktList.add(tkt);
		}

		return tktList;

	}

	public Long deleteTicketById(long id) {

		try {
			ticketRepo.deleteById(id);
			return id;
		} catch (Exception ex) {
			throw new ItemNotFoundException("Data Unavailable", ex);
		}

	}

	public Ticket updateTicketById(Ticket tkt, long id) {

		Optional<Ticket> tktEntity = ticketRepo.findById(id);
		if (tktEntity.isPresent()) {

			Ticket response = ticketRepo.save(tkt);
			return response;
		} else {
			throw new ItemNotFoundException("Item not found");
		}

	}

	public void deleteAllEmployee() {

		try {
			ticketRepo.deleteAll();
		} catch (Exception ex) {
			throw new ItemNotFoundException("Data Unavailable", ex);
		}
	}

	public Ticket assignTicketTo(long ticket_id, Employee emp) {

		Optional<Ticket> ticket = ticketRepo.findById(ticket_id);
		if (ticket.isPresent()) {
			Ticket ticketEntity = ticket.get();
			ticketEntity.setEmployee(emp);
			Ticket response = ticketRepo.save(ticketEntity);
			return response;
		} else {
			throw new ItemNotFoundException("Ticket Details Not Found");
		}
	}

	public List<Ticket> findTicketByEmpId(long emp_id) {
		List<Ticket> ticket = ticketRepo.findByEmpId(emp_id);
		return ticket;
	}

	public Ticket updateStatus(long ticket_id, TicketStatus newStatus) {
		Optional<Ticket> ticket = ticketRepo.findById(ticket_id);
		if (ticket.isPresent()) {
			Ticket ticketEntity = ticket.get();
			Boolean correctStatus = false;
			switch (ticketEntity.getTicket_status()) {

			case OPEN:
				if (newStatus.equals(TicketStatus.INPROGRESS) || newStatus.equals(TicketStatus.CLOSED))
					correctStatus = true;
				break;
			case CLOSED:
				if (newStatus.equals(TicketStatus.REOPENED))
					correctStatus = true;
				break;
			case INPROGRESS:
				if (newStatus.equals(TicketStatus.CLOSED))
					correctStatus = true;
				break;
			case REOPENED:
				if (newStatus.equals(TicketStatus.INPROGRESS) || newStatus.equals(TicketStatus.CLOSED))
					correctStatus = true;
				break;
			}

			if (correctStatus == true) {
				ticketEntity.setTicket_status(newStatus);
			}else {
				throw new ItemNotFoundException("Wrong Ticket status");
			}
			Ticket response = ticketRepo.save(ticketEntity);
			return response;
		} else {
			throw new ItemNotFoundException("Ticket Details Not Found");
		}
	}

}
