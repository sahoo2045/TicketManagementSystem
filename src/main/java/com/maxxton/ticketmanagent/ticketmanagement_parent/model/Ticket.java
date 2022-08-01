package com.maxxton.ticketmanagent.ticketmanagement_parent.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/*
 * @author - Ashutosh Sahoo
 */

@Entity
@Table(name = "ticket")
@JsonInclude(Include.NON_NULL)
public class Ticket {

	@Id
	@Column(name = "ticketId")
	private Long ticketId;
	@NonNull
	@Column(name = "summary")
	private String summary;
	@NonNull
	@Column(name = "description")
	private String description;
	@OneToMany(mappedBy = "ticket", cascade = { CascadeType.ALL })
	private Set<TicketAssignToDao> assignTo = new HashSet<>();;
	@NonNull
	@Column(name = "createdBy")
	private long createdBy;
	@NonNull
	@Column(name = "priority")
	private int priority;
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "creationDate")
	private Date creationDate;
	@JsonFormat(pattern = "yyyy-MM-dd")
	@Column(name = "lastUpdate")
	private Date lastUpdate;
	@OneToMany(mappedBy = "ticket", cascade = { CascadeType.ALL })
	private Set<Comment> comments = new HashSet<>();
	@Enumerated(EnumType.STRING)
	@NonNull
	@Column(name = "ticketStatus")
	private TicketStatus ticket_status;
	@NonNull
	@Column(name = "ticketType")
	private String ticket_type;
	
	

	public long getTicketId() {
		return ticketId;
	}

	public void setTicketId(long ticketId) {
		this.ticketId = ticketId;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<TicketAssignToDao> getAssignTo() {
		return assignTo;
	}

	public void setAssignTo(Set<TicketAssignToDao> assignTo) {
		this.assignTo = assignTo;
	}

	public long getCreatedBy() {
		return createdBy;
	}
	

	public void setCreatedBy(long createdBy) {
		this.createdBy = createdBy;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}

	public TicketStatus getTicket_status() {
		return ticket_status;
	}

	public void setTicket_status(TicketStatus ticket_status) {
		this.ticket_status = ticket_status;
	}

	public String getTicket_type() {
		return ticket_type;
	}

	public void setTicket_type(String ticket_type) {
		this.ticket_type = ticket_type;
	}

}
