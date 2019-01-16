package com.fblaise.sms.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "schoolyear")
public class SchoolYear extends SmsDomain {
	@Column(name = "name", unique = true)
	private String name;
	@Temporal(TemporalType.DATE)
	@Column(name = "startdate")
	private Date startDate;
	@Temporal(TemporalType.DATE)
	@Column(name = "enddate")
	private Date endDate;
	@Enumerated(EnumType.STRING)
	private ElementState state;
	@JsonIgnore 
	@OneToMany(mappedBy = "schoolYear", cascade = { CascadeType.REMOVE })
	private List<SchoolYearPart> schoolYearParts = new ArrayList<>();
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "schoolyear_classroom", joinColumns = @JoinColumn(name = "schoolyear_id") , inverseJoinColumns = @JoinColumn(name = "classroom_id") )
	private Set<Classroom> classrooms;

	public SchoolYear() {
	}

	public SchoolYear(String name, Date startDate, Date endDate) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.name = name;
		this.state = ElementState.Not_opened;
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

	public ElementState getState() {
		return state;
	}

	public void setState(ElementState state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SchoolYearPart> getSchoolYearParts() {
		return schoolYearParts;
	}

	public void setSchoolYearParts(List<SchoolYearPart> schoolYearParts) {
		this.schoolYearParts = schoolYearParts;
	}

	public void copyValuesFrom(SchoolYear schoolYearWithNewValues) {
		this.startDate = schoolYearWithNewValues.startDate;
		this.endDate = schoolYearWithNewValues.endDate;
		this.name = schoolYearWithNewValues.name;
	}

	public void removeSchoolYearPart(SchoolYearPart part) {
		if (schoolYearParts.contains(part)) {
			schoolYearParts.remove(part);
		}
	}

	public void addSchoolYearPart(SchoolYearPart part) {
		if (!schoolYearParts.contains(part)) {
			schoolYearParts.add(part);
		}
	}

	public void addClassroom(Classroom classroom) {
		if (!classrooms.contains(classroom)) {
			classrooms.add(classroom);
		}
	}

	public void removeClassroom(Classroom classroom) {
		if (classrooms.contains(classroom)) {
			classrooms.remove(classroom);
		}
	}

	public Set<Classroom> getClassrooms() {
		return classrooms;
	}

	public void setClassrooms(Set<Classroom> classrooms) {
		this.classrooms = classrooms;
	}

	public boolean endDateIsBeforeStartDate() {
		return endDate.before(startDate);
	}

}
