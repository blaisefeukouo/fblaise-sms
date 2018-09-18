package com.fblaise.sms.model;

public class SmsConfigObject {
	private SchoolYear currentSchoolYear;

	public SmsConfigObject() {
		super();
	}

	public SmsConfigObject(SchoolYear currentSchoolYear) {
		super();
		this.currentSchoolYear = currentSchoolYear;
	}

	public SchoolYear getCurrentSchoolYear() {
		return currentSchoolYear;
	}

	public void setCurrentSchoolYear(SchoolYear currentSchoolYear) {
		this.currentSchoolYear = currentSchoolYear;
	}

}
