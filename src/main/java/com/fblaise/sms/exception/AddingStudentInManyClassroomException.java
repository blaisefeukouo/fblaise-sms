package com.fblaise.sms.exception;

public class AddingStudentInManyClassroomException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AddingStudentInManyClassroomException() {
		super("A student cannot be in more than one classroom during a school year");
	}

}
