package com.fblaise.sms.model;

import java.util.Date;

public class UiSchoolYearPart {
	private Long id;
	private String name;
	private String startDate;
	private String endDate;
	private Date realStartDate;
	private Date realEndDate;
	private Long schoolYearId;

	public UiSchoolYearPart() {
	}

	public UiSchoolYearPart(Long schoolYearId) {
		this.schoolYearId = schoolYearId;
	}

	public UiSchoolYearPart(String name, String startDate, String endDate) {
		super();
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public UiSchoolYearPart(Long id, String name, String startDate, String endDate) {
		this.id = id;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Date getRealStartDate() {
		return realStartDate;
	}

	public void setRealStartDate(Date realStartDate) {
		this.realStartDate = realStartDate;
	}

	public Date getRealEndDate() {
		return realEndDate;
	}

	public void setRealEndDate(Date realEndDate) {
		this.realEndDate = realEndDate;
	}

	public Long getSchoolYearId() {
		return schoolYearId;
	}

	public void setSchoolYearId(Long schoolYearId) {
		this.schoolYearId = schoolYearId;
	}

}