package com.fblaise.sms.model;

import java.util.Date;

public class UiSchoolYear {
	private Long id;
	private String name;
	private String startDate;
	private String endDate;
	private Date realStartDate;
	private Date realEndDate;
	private ElementState state;

	public UiSchoolYear() {
	}

	public UiSchoolYear(String name, String startDate, String endDate) {
		super();
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public UiSchoolYear(Long id, String name, String startDate, String endDate, ElementState state) {
		this.id = id;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.state = state;
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

	public ElementState getState() {
		return state;
	}

	public void setState(ElementState state) {
		this.state = state;
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

}