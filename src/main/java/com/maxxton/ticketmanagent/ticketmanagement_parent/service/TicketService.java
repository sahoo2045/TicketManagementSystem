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
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxxton.ticketmanagent.ticketmanagement_parent.exceptions.BadRequest;
import com.maxxton.ticketmanagent.ticketmanagement_parent.exceptions.InvalidAssigneeException;
import com.maxxton.ticketmanagent.ticketmanagement_parent.exceptions.InvalidStatusException;
import com.maxxton.ticketmanagent.ticketmanagement_parent.exceptions.ItemNotFoundException;
import com.maxxton.ticketmanagent.ticketmanagement_parent.model.Comment;
import com.maxxton.ticketmanagent.ticketmanagement_parent.model.Employee;
import com.maxxton.ticketmanagent.ticketmanagement_parent.model.Ticket;
import com.maxxton.ticketmanagent.ticketmanagement_parent.model.TicketAssignToDao;
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
		tkt.setTicketId(ThreadLocalRandom.current().nextInt(1000, 10000));
		tkt.setCreationDate(new Date());
		boolean userExists = employeeRepo.existsById(tkt.getCreatedBy());
		if (userExists) {
			if (tkt.getTicket_status() == TicketStatus.OPEN) {
				if ((null == tkt.getAssignTo() || tkt.getAssignTo().isEmpty())
						&& (null == tkt.getComments() || tkt.getComments().isEmpty())) {
					response = ticketRepo.saveAndFlush(tkt);
				} else {
					throw new BadRequest("Ticket Assignment and Comment entry is not allowed during create operation");
				}

			} else {
				throw new InvalidStatusException("Ticket Status Should be in Open State");
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
			if (tkt.getTicket_status() != tktEntity.get().getTicket_status()) {
				throw new InvalidStatusException("Status Update Not Allowed");
			}
			tkt.setCreatedBy(tktEntity.get().getCreatedBy());
			Set<TicketAssignToDao> assignSetDto = new HashSet<>();

			/* Check for Asinee from DB */
			if (null != tktEntity.get().getAssignTo() && !(tktEntity.get().getAssignTo().isEmpty())) {
				tkt.setAssignTo(tktEntity.get().getAssignTo());

			} else {
				if (null != tkt.getAssignTo() && !(tkt.getAssignTo().isEmpty())) {
					throw new BadRequest("Ticket Assign is not allowed during current update operation");
				}
			}

			/* Check for Asinee from DB */
			if (null != tktEntity.get().getComments() && !(tktEntity.get().getComments().isEmpty())) {
				tkt.setComments(tktEntity.get().getComments());

			} else {
				if (null != tkt.getComments() && !(tkt.getComments().isEmpty())) {
					throw new BadRequest("Comment is not allowed during current update operation");
				}
			}
			tkt.setLastUpdate(new Date());
			Ticket response = ticketRepo.saveAndFlush(tkt);
			return response;
		} else {
			throw new ItemNotFoundException("Item not found");
		}
	}

	public Ticket findTicketById(long id) {
		Optional<Ticket> tkt_id = ticketRepo.findById(id);
		if (!tkt_id.isPresent()) {
			throw new ItemNotFoundException("Ticket Details Not Found");
		} else {
			Ticket ticketEntity = tkt_id.get();
			ObjectMapper mapper = new ObjectMapper();
			Ticket response = mapper.convertValue(ticketEntity, Ticket.class);
			logger.info("User details found...");
			return response;
		}

	}

	public List<Ticket> findAllTickets() {
		Iterable<Ticket> ticketEntity = ticketRepo.findAll();
		List<Ticket> tktList = new ArrayList<>();
		if (null == ticketEntity) {
			throw new ItemNotFoundException("Ticket Details Not Found");
		} else {
			for (Ticket ticket : ticketEntity) {
				ObjectMapper mapper = new ObjectMapper();
				Ticket tkt = mapper.convertValue(ticket, Ticket.class);
				tktList.add(tkt);
			}
			return tktList;
		}

	}

	public Long deleteTicketById(long id) throws Exception {
		try {
			List<TicketAssignToDao> tktAsignee = ticketAssignToRepo.findByTicketId(id);
			if (null != tktAsignee && !(tktAsignee.isEmpty())) {

				for (TicketAssignToDao assign : tktAsignee) {
					if (!assign.isCurrentAssignee()) {
						ticketAssignToRepo.deleteByTicketAndEmployeeId(assign.getTicket().getTicketId(),
								assign.getEmployee().getId());
					}
				}
			}

			ticketRepo.deleteById(id);
			return id;
		} catch (DataIntegrityViolationException ex) {
			throw new DataIntegrityViolationException("Kindly delete all the refrence for this ticket ID");
		} catch (Exception ex) {
			throw new Exception(ex);
		}

	}

	public void deleteAllTicket() throws Exception {

		try {
			ticketRepo.deleteAll();
		} catch (Exception ex) {
			throw new Exception(ex);
		}
	}

	public TicketAssignToDao assignTicketTo(long ticket_id, Employee emp) {
		boolean employeeExistsForAssignment = employeeRepo.existsById(emp.getId());
		if (!employeeExistsForAssignment) {
			throw new ItemNotFoundException("Employee Details Not Found");
		}

		Optional<Ticket> ticket = ticketRepo.findById(ticket_id);
		if (ticket.isPresent()) {
			Ticket ticketEntity = ticket.get();
			TicketAssignToDao response = new TicketAssignToDao();

			if (null != ticketEntity.getAssignTo() && !(ticketEntity.getAssignTo().isEmpty())) {
				Set<TicketAssignToDao> oldAssignmentSet = ticketEntity.getAssignTo();
				boolean employeeExists = false;
				long existingID = 0;
				double existingLogWorkHours = 0;
				for (TicketAssignToDao assigned : oldAssignmentSet) {
					assigned.setCurrentAssignee(false);
					if (assigned.getEmployee().getId() == emp.getId()) {
						employeeExists = true;
						existingID = assigned.getId();
						existingLogWorkHours = assigned.getLogWorkHours();
					}
				}
				TicketAssignToDao assignDao = new TicketAssignToDao();
				if (employeeExists) {
					assignDao.setId(existingID);
					assignDao.setLogWorkHours(existingLogWorkHours);
				} else {
					assignDao.setLogWorkHours(0);
				}
				assignDao.setCurrentAssignee(true);
				assignDao.setEmployee(emp);
				assignDao.setTicket(ticketEntity);
				response = ticketAssignToRepo.save(assignDao);
			} else {
				TicketAssignToDao assignDao = new TicketAssignToDao();
				assignDao.setCurrentAssignee(true);
				assignDao.setEmployee(emp);
				assignDao.setLogWorkHours(0);
				assignDao.setTicket(ticketEntity);
				response = ticketAssignToRepo.save(assignDao);

			}
			return response;
		} else {
			throw new ItemNotFoundException("Ticket Details Not Found");
		}

	}

	public List<Ticket> findTicketByEmpId(long emp_id) {

		Optional<Employee> employeeOpt = employeeRepo.findById(emp_id);
		if (employeeOpt.isPresent()) {
			List<Ticket> ticketList = new ArrayList<>();
			Set<TicketAssignToDao> ticketAssignList = ticketAssignToRepo.findByTicketAssignTo_Employee(emp_id);
			for (TicketAssignToDao assign : ticketAssignList) {
				Ticket ticketDetails = findTicketById(assign.getTicket().getTicketId());
				ticketList.add(ticketDetails);
			}
			return ticketList;
		} else {
			throw new ItemNotFoundException("No employee details found");
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
				throw new InvalidStatusException("Invalid Ticket Status Sequence.");
			}
			ticketEntity.setLastUpdate(new Date());
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
			TicketAssignToDao assignToEmployee = new TicketAssignToDao();
			Set<TicketAssignToDao> assignToSet = new HashSet<>();
			if (null != ticketEntity.getAssignTo() || !ticketEntity.getAssignTo().isEmpty()) {
				boolean correctEmployee = false;
				for (TicketAssignToDao assign : ticketEntity.getAssignTo()) {
					if (assign.getEmployee().getId() == emp.getId()) {
						assignToEmployee = assign;
						correctEmployee = true;
						break;
					}
				}
				if (correctEmployee == true && assignToEmployee.isCurrentAssignee()) {

					assignToEmployee.setLogWorkHours(assignToEmployee.getLogWorkHours() + workHours);
					assignToSet.add(assignToEmployee);
					ticketEntity.setAssignTo(assignToSet);
					response = ticketRepo.saveAndFlush(ticketEntity);
				} else {
					throw new InvalidAssigneeException("The User Provided is not allocated currently to this ticket.");
				}
			} else {
				throw new InvalidAssigneeException("Ticket Not Assigned To Anyone yet.");
			}
		} else {
			throw new ItemNotFoundException("Ticket not found");
		}

		return response;
	}

	public Ticket saveComment(@Valid Comment comment) {
		Optional<Ticket> ticket = ticketRepo.findById(comment.getTicket().getTicketId());
		if (ticket.isPresent()) {
			Ticket ticketEntity = ticket.get();
			commentRepo.saveAndFlush(comment);
			ticketEntity.setLastUpdate(new Date());
			Ticket response = ticketRepo.saveAndFlush(ticketEntity);
			return response;
		} else {
			throw new ItemNotFoundException("Ticket Details Not Found");
		}
	}
}
