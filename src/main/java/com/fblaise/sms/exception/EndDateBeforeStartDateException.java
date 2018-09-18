package com.fblaise.sms.exception;

public class EndDateBeforeStartDateException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EndDateBeforeStartDateException() {
		super("The end date should not be before start date");
	}

}
