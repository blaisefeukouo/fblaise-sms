package com.fblaise.sms.service;

import java.util.Date;
import java.util.List;

import com.fblaise.sms.model.SchoolYear;

public interface SchoolYearService {

	public SchoolYear findSchoolYearById(Long id);

	public List<SchoolYear> findAllSchoolYears();

	public Long saveSchoolYear(SchoolYear schoolYear);

	public Long updateSchoolYear(SchoolYear schoolYearWithNewValues);

	public void deleteSchoolYear(Long id);

	public SchoolYear removeSchoolYearPart(Long schoolYearId, Long schoolYearPartId);

	public SchoolYear addSchoolYearPart(SchoolYear schoolYear, String name, Date startDate, Date endDate);

	public SchoolYear editSchoolYearPart(Long id, String name, Date startDate, Date endDate);

	public SchoolYear findOpenedSchoolYear();

	public void openSchoolYear(Long schoolYearId);

	public void closeSchoolYear(Long schoolYearId);

	public SchoolYear addClassroomToSchoolYear(Long schoolYearId, Long classroomId);

	public SchoolYear removeClassroomToSchoolYear(Long schoolYearId, Long classroomId);

}