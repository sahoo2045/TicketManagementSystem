package com.maxxton.ticketmanagent.ticketmanagement_parent.exceptions;

import java.util.Date;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import springfox.documentation.annotations.ApiIgnore;

@ControllerAdvice
@ApiIgnore
public class ExceptionHandlerController {

	@ExceptionHandler
	public ResponseEntity<ItemErrorResponse> handleException(ItemNotFoundException ine) {
		ItemErrorResponse errorResponse = new ItemErrorResponse();
		errorResponse.setStatus(HttpStatus.NOT_FOUND.value());
		errorResponse.setMessage(ine.getMessage());
		errorResponse.setTimeStamp(new Date());
		return new ResponseEntity<ItemErrorResponse>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler
	public ResponseEntity<ItemErrorResponse> handleException(DataIntegrityViolationException ex) {
		ItemErrorResponse errorResponse = new ItemErrorResponse();
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setTimeStamp(new Date());
		return new ResponseEntity<ItemErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<ItemErrorResponse> handleException(InvalidIdException ex) {
		ItemErrorResponse errorResponse = new ItemErrorResponse();
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setTimeStamp(new Date());
		return new ResponseEntity<ItemErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<ItemErrorResponse> handleException(PasswordMismatchEception ex) {
		ItemErrorResponse errorResponse = new ItemErrorResponse();
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setTimeStamp(new Date());
		return new ResponseEntity<ItemErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<ItemErrorResponse> handleException(InvalidAssigneeException ex) {
		ItemErrorResponse errorResponse = new ItemErrorResponse();
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setTimeStamp(new Date());
		return new ResponseEntity<ItemErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<ItemErrorResponse> handleException(InvalidStatusException ex) {
		ItemErrorResponse errorResponse = new ItemErrorResponse();
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setTimeStamp(new Date());
		return new ResponseEntity<ItemErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<ItemErrorResponse> handleException(Exception ex) {
		ItemErrorResponse errorResponse = new ItemErrorResponse();
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setTimeStamp(new Date());
		return new ResponseEntity<ItemErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler
	public ResponseEntity<ItemErrorResponse> handleException(BadRequest ex) {
		ItemErrorResponse errorResponse = new ItemErrorResponse();
		errorResponse.setStatus(HttpStatus.BAD_REQUEST.value());
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setTimeStamp(new Date());
		return new ResponseEntity<ItemErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
	}
}
