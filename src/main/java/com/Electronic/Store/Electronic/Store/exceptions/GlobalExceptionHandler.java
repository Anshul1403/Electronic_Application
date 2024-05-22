package com.Electronic.Store.Electronic.Store.exceptions;

import org.hibernate.validator.internal.util.logging.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.Electronic.Store.Electronic.Store.dtos.ApiResponseMessage;

import ch.qos.logback.classic.Logger;

@RestControllerAdvice
public class GlobalExceptionHandler {
  
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponseMessage> resourceNotfoundExceptionHandler(ResourceNotFoundException ex){
		
	ApiResponseMessage response = ApiResponseMessage.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND).success(true).build();
	
	return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(BadApiRequest.class)
	public ResponseEntity<ApiResponseMessage> handleBadApiRequest(BadApiRequest ex){
		
	ApiResponseMessage response = ApiResponseMessage.builder().message(ex.getMessage()).status(HttpStatus.BAD_REQUEST).success(false).build();
	
	return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
	}
}
