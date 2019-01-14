package com.fblaise.sms.exception;

public class DupplicateSchoolYearNameException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DupplicateSchoolYearNameException() {
		super("You cannot have two school years with same name in the system");
	}

}
