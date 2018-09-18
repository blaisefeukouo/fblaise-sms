package com.fblaise.sms.exception;

public class DupplicateUserNameException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DupplicateUserNameException() {
		super("You cannot hahe 2 users with same userName");
	}

}
