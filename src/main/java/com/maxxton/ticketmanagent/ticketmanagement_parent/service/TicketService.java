package com.maxxton.ticketmanagent.ticketmanagement_parent.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxxton.ticketmanagent.ticketmanagement_parent.exceptions.ItemNotFoundException;
import com.maxxton.ticketmanagent.ticketmanagement_parent.model.Employee;
import com.maxxton.ticketmanagent.ticketmanagement_parent.model.Ticket;
import com.maxxton.ticketmanagent.ticketmanagement_parent.model.TicketStatus;
import com.maxxton.ticketmanagent.ticketmanagement_parent.repo.EmployeeRepo;
import com.maxxton.ticketmanagent.ticketmanagement_parent.repo.TicketRepo;

@Service
public class TicketService {

	@Autowired
	TicketRepo ticketRepo;

	@Autowired
	EmployeeRepo employeeRepo;

	Logger logger = LoggerFactory.getLogger(UserService.class);

	public Ticket createTicket(Ticket tkt) {
		Ticket response = new Ticket();
		tkt.setTicket_no(ThreadLocalRandom.current().nextInt(1000, 10000));
		tkt.setTicket_creationDate(new Date());
		boolean userExists = employeeRepo.existsById(tkt.getTicket_createdBy());
		if (userExists) {
			if (tkt.getTicket_status() == TicketStatus.OPEN) {
				response = ticketRepo.save(tkt);
			} else {
				throw new ItemNotFoundException("Ticket Status Should be in Open State");
			}

		} else {
			throw new ItemNotFoundException("Created By User Not Found");
		}
		logger.info("User Credentials created...");
		return response;
	}

	public Ticket updateTicketById(Ticket tkt, long id) {
		Optional<Ticket> tktEntity = ticketRepo.findById(id);
		if (tktEntity.isPresent()) {
			tkt.setTicket_lastUpdate(new Date());
			if (tkt.getTicket_status() != tktEntity.get().getTicket_status()) {
				throw new ItemNotFoundException("Status Update Not Allowed");
			}
			tkt.setTicket_createdBy(tktEntity.get().getTicket_createdBy());
			Ticket response = ticketRepo.save(tkt);
			return response;
		} else {
			throw new ItemNotFoundException("Item not found");
		}
	}

	public Ticket findTicketById(long id) {
		Optional<Ticket> tkt_id = ticketRepo.findById(id);
		Ticket response = tkt_id.get();
		logger.info("User details found...");
		return response;
	}

	public List<Ticket> findAllTickets() {
		Iterable<Ticket> ticketEntity = ticketRepo.findAll();
		List<Ticket> tktList = new ArrayList<>();
		for (Ticket ticket : ticketEntity) {
			ObjectMapper mapper = new ObjectMapper();
			Ticket tkt = mapper.convertValue(ticket, Ticket.class);
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
			Set<Employee> employeeSet = new HashSet<>();
			employeeSet.add(emp);
			ticketEntity.setEmployee(employeeSet);
			ticketEntity.setTicket_lastUpdate(new Date());
			Ticket response = ticketRepo.save(ticketEntity);
			return response;
		} else {
			throw new ItemNotFoundException("Ticket Details Not Found");
		}
	}

	public List<Ticket> findTicketByEmpId(long emp_id) {

		Set<Employee> employeeSet = new HashSet<>();
		List<Ticket> ticketList = new ArrayList<>();
		Optional<Employee> employeeOpt = employeeRepo.findById(emp_id);
		if (employeeOpt.isPresent()) {
			employeeSet.add(employeeOpt.get());
			ticketList = ticketRepo.findByEmployeeIn(employeeSet);
			if (ticketList.isEmpty()) {
				throw new ItemNotFoundException("Ticket Details Not Found");
			}
			return ticketList;
		} else {
			throw new ItemNotFoundException("Employee Details Not Found");
		}
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
			default:
				if (newStatus.equals(TicketStatus.OPEN))
					correctStatus = true;
				break;
			}

			if (correctStatus == true) {
				ticketEntity.setTicket_status(newStatus);
			} else {
				throw new ItemNotFoundException("Wrong Ticket status");
			}
			ticketEntity.setTicket_lastUpdate(new Date());
			if (ticketEntity.getTicket_status() == TicketStatus.CLOSED) {
				ticketEntity.setTicket_endDate(new Date());
			}
			Ticket response = ticketRepo.save(ticketEntity);
			return response;
		} else {
			throw new ItemNotFoundException("Ticket Details Not Found");
		}
	}

	public Ticket updateWorkingHours(long ticket_id, Employee emp, double workHours) {
		Optional<Ticket> ticket = ticketRepo.findById(ticket_id);
		Ticket response = new Ticket();
		if (ticket.isPresent()) {
			Ticket ticketEntity = ticket.get();
			if (null != ticketEntity.getEmployee() || !ticketEntity.getEmployee().isEmpty()) {
				boolean correctEmployee = false;
				for (Employee emEntity : ticketEntity.getEmployee()) {
					if (emEntity.getEmp_id() == emp.getEmp_id()) {
						correctEmployee = true;
						break;
					}
				}
				if (ticketEntity.isCurrentAssignee()) {
					ticketEntity.setLogWorkHours(ticketEntity.getLogWorkHours() + workHours);
					response = ticketRepo.save(ticketEntity);
				}
			}
		}

		return response;
	}

}
