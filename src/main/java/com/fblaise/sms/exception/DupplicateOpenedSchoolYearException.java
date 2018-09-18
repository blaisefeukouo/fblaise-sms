package com.fblaise.sms.exception;

public class DupplicateOpenedSchoolYearException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DupplicateOpenedSchoolYearException() {
		super("You cannot have more than one opened school year in the system");
	}

}
