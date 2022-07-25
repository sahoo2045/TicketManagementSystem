package com.maxxton.ticketmanagent.ticketmanagement_parent.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxxton.ticketmanagent.ticketmanagement_parent.exceptions.ItemNotFoundException;
import com.maxxton.ticketmanagent.ticketmanagement_parent.model.Comment;
import com.maxxton.ticketmanagent.ticketmanagement_parent.model.Employee;
import com.maxxton.ticketmanagent.ticketmanagement_parent.model.Ticket;
import com.maxxton.ticketmanagent.ticketmanagement_parent.model.TicketAssignTo;
import com.maxxton.ticketmanagent.ticketmanagement_parent.model.TicketStatus;
import com.maxxton.ticketmanagent.ticketmanagement_parent.repo.CommentRepo;
import com.maxxton.ticketmanagent.ticketmanagement_parent.repo.EmployeeRepo;
import com.maxxton.ticketmanagent.ticketmanagement_parent.repo.TicketAssignToRepo;
import com.maxxton.ticketmanagent.ticketmanagement_parent.repo.TicketRepo;

@Service
public class TicketService {

	@Autowired
	private TicketRepo ticketRepo;

	@Autowired
	private EmployeeRepo employeeRepo;

	@Autowired
	private CommentRepo commentRepo;

	@Autowired
	private TicketAssignToRepo ticketAssignToRepo;

	Logger logger = LoggerFactory.getLogger(UserService.class);

	public Ticket createTicket(Ticket tkt) {
		Ticket response = new Ticket();
		tkt.setTicket_id(ThreadLocalRandom.current().nextInt(1000, 10000));
		tkt.setTicket_creationDate(new Date());
		boolean userExists = employeeRepo.existsById(tkt.getTicket_createdBy());
		if (userExists) {
			if (tkt.getTicket_status() == TicketStatus.OPEN) {
				response = ticketRepo.saveAndFlush(tkt);
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
			Ticket response = ticketRepo.saveAndFlush(tkt);
			return response;
		} else {
			throw new ItemNotFoundException("Item not found");
		}
	}

	public Ticket findTicketById(long id) {
		Optional<Ticket> tkt_id = ticketRepo.findById(id);
		Ticket ticketEntity = tkt_id.get();
		ObjectMapper mapper = new ObjectMapper();
		Ticket response = mapper.convertValue(ticketEntity, Ticket.class);
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
			Set<TicketAssignTo> ticketAssignToSet = new HashSet<>();
			TicketAssignTo assignToEmployee = new TicketAssignTo();

			if (null != ticketEntity.getTicketAssignTo() || !ticketEntity.getTicketAssignTo().isEmpty()) {
				boolean correctEmployee = false;
				for (TicketAssignTo assign : ticketEntity.getTicketAssignTo()) {
					if (assign.getEmployee().getEmp_id() == emp.getEmp_id()) {
						assignToEmployee = assign;
						correctEmployee = true;
						break;
					}
					if (assign.isCurrentAssignee()) {
						assignToEmployee = assign;
					}
				}
				if (correctEmployee && assignToEmployee.isCurrentAssignee()) {
					throw new ItemNotFoundException("The User Provided is already allocated currently to this ticket.");
				} else if (correctEmployee && !assignToEmployee.isCurrentAssignee()) {
					return assign(emp, ticketEntity, assignToEmployee);
				} else {
					return assign(emp, ticketEntity, assignToEmployee);
				}

			} else {
				TicketAssignTo ticketAssignTo = new TicketAssignTo();
				ticketAssignTo.setEmployee(emp);
				ticketAssignTo.setTicket(ticketEntity);
				ticketAssignTo.setLogWorkHours(0);
				ticketAssignTo.setCurrentAssignee(true);
				ticketAssignToSet.add(ticketAssignTo);
				ticketEntity.setTicketAssignTo(ticketAssignToSet);
				ticketEntity.setTicket_lastUpdate(new Date());
				Ticket response = ticketRepo.saveAndFlush(ticketEntity);
				return response;
			}
		} else {
			throw new ItemNotFoundException("Ticket Not Found.");
		}
	}

	private Ticket assign(Employee emp, Ticket ticketEntity, TicketAssignTo assignToEmployee) {

		Set<TicketAssignTo> ticketAssignToSet = new HashSet<>();
		TicketAssignTo ticketAssignTo = new TicketAssignTo();
		ticketAssignTo.setEmployee(emp);
		ticketAssignTo.setTicket(ticketEntity);
		ticketAssignTo.setLogWorkHours(0);
		ticketAssignTo.setCurrentAssignee(true);
		/* For Current User */
		assignToEmployee.setCurrentAssignee(false);
		ticketAssignToSet.add(assignToEmployee);
		ticketEntity.setTicketAssignTo(ticketAssignToSet);
		ticketEntity.setTicket_lastUpdate(new Date());
		ticketRepo.saveAndFlush(ticketEntity);
		/* For New User */
		ticketAssignToSet = new HashSet<>();
		ticketAssignToSet.add(ticketAssignTo);
		ticketEntity.setTicketAssignTo(ticketAssignToSet);
		ticketEntity.setTicket_lastUpdate(new Date());
		Ticket response = ticketRepo.saveAndFlush(ticketEntity);
		return response;

	}

	public List<Ticket> findTicketByEmpId(long emp_id) {

		Optional<Employee> employeeOpt = employeeRepo.findById(emp_id);
		if (employeeOpt.isPresent()) {
			List<Ticket> ticketList = new ArrayList<>();
			Set<TicketAssignTo> ticketAssignList = ticketAssignToRepo.findByTicketAssignTo_Employee(emp_id);
			for (TicketAssignTo assign : ticketAssignList) {
				Ticket ticketDetails = findTicketById(assign.getTicket().getTicket_id());
				ticketList.add(ticketDetails);
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
			Ticket response = ticketRepo.saveAndFlush(ticketEntity);
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
			TicketAssignTo assignToEmployee = new TicketAssignTo();
			Set<TicketAssignTo> assignToSet = new HashSet<>();
			if (null != ticketEntity.getTicketAssignTo() || !ticketEntity.getTicketAssignTo().isEmpty()) {
				boolean correctEmployee = false;
				for (TicketAssignTo assign : ticketEntity.getTicketAssignTo()) {
					if (assign.getEmployee().getEmp_id() == emp.getEmp_id()) {
						assignToEmployee = assign;
						correctEmployee = true;
						break;
					}
				}
				if (correctEmployee == true && assignToEmployee.isCurrentAssignee()) {
					assignToEmployee.setLogWorkHours(assignToEmployee.getLogWorkHours() + workHours);
					assignToSet.add(assignToEmployee);
					ticketEntity.setTicketAssignTo(assignToSet);
					response = ticketRepo.saveAndFlush(ticketEntity);
				} else {
					throw new ItemNotFoundException("The User Provided is not allocated currently to this ticket.");
				}
			} else {
				throw new ItemNotFoundException("Ticket Not Assigned To Anyone yet.");
			}
		}

		return response;
	}

	public Ticket saveComment(@Valid Comment comment) {
		Optional<Ticket> ticket = ticketRepo.findById(comment.getTicket().getTicket_id());
		if (ticket.isPresent()) {
			Ticket ticketEntity = ticket.get();
			commentRepo.saveAndFlush(comment);
			ticketEntity.setTicket_lastUpdate(new Date());
			Ticket response = ticketRepo.saveAndFlush(ticketEntity);
			return response;
		} else {
			throw new ItemNotFoundException("Ticket Details Not Found");
		}
	}
}
