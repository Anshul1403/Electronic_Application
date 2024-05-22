package com.Electronic.Store.Electronic.Store.exceptions;

import lombok.Builder;

@Builder
public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException() {
		super("Resorce Not Found");
	}
	
	public ResourceNotFoundException(String message) {
		super(message);
	}
}
