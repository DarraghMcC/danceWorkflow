package com.api;

public class SuccessfulResponse implements Response {

	private String successMessage;

	public SuccessfulResponse() {
	}
	
	public SuccessfulResponse(final String successMessage) {
		this.successMessage = successMessage;
	}
	
	public String getSuccessMessage() {
		return successMessage;
	}

	public void setSuccessMessage(final String successMessage) {
		this.successMessage = successMessage;
	}

}
