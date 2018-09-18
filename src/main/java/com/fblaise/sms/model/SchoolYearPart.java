package com.fblaise.sms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "schoolyearpart")
public class SchoolYearPart extends SmsDomain {

	private String name;
	@Temporal(TemporalType.DATE)
	@Column(name = "startdate")
	private Date startDate;
	@Temporal(TemporalType.DATE)
	@Column(name = "enddate")
	private Date endDate;
	@ManyToOne
	@JoinColumn(name = "schoolyear_id", nullable = false)
	private SchoolYear schoolYear;

	public SchoolYearPart() {
	}

	public SchoolYearPart(SchoolYear schoolYear, String name, Date startDate, Date endDate) {
		super();
		this.schoolYear = schoolYear;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public SchoolYearPart(String name, Date startDate, Date endDate) {
		super();
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public SchoolYear getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(SchoolYear schoolYear) {
		this.schoolYear = schoolYear;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void copyValuesFrom(SchoolYearPart schoolYearWithNewValues) {
		this.name = schoolYearWithNewValues.name;
		this.startDate = schoolYearWithNewValues.startDate;
		this.endDate = schoolYearWithNewValues.endDate;
	}

	public Long getSchoolYearId() {
		if (schoolYear != null) {
			return schoolYear.getId();
		}
		return null;
	}

	public boolean endDateIsBeforeStartDate() {
		return endDate.before(startDate);
	}

}
