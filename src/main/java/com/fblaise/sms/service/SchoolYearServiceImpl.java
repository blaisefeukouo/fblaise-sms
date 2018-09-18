package com.fblaise.sms.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fblaise.sms.exception.DupplicateOpenedSchoolYearException;
import com.fblaise.sms.exception.EndDateBeforeStartDateException;
import com.fblaise.sms.model.Classroom;
import com.fblaise.sms.model.ElementState;
import com.fblaise.sms.model.SchoolYear;
import com.fblaise.sms.model.SchoolYearPart;
import com.fblaise.sms.repository.ClassroomRepository;
import com.fblaise.sms.repository.SchoolYearPartRepository;
import com.fblaise.sms.repository.SchoolYearRepository;

@Service("schoolYearServiceImpl")
public class SchoolYearServiceImpl implements SchoolYearService {

	@Autowired
	private SchoolYearRepository schoolYearRepository;
	@Autowired
	private SchoolYearPartRepository schoolYearPartRepository;
	@Autowired
	private ClassroomRepository classroomRepository;

	@Override
	public SchoolYear findSchoolYearById(Long id) {
		return schoolYearRepository.findById(id);
	}

	@Override
	public List<SchoolYear> findAllSchoolYears() {
		return schoolYearRepository.findAll();
	}

	@Override
	public Long saveSchoolYear(SchoolYear schoolYear) {
		if (schoolYear.endDateIsBeforeStartDate()) {
			throw new EndDateBeforeStartDateException();
		}
		schoolYear = schoolYearRepository.save(schoolYear);
		return schoolYear.getId();
	}

	@Override
	public Long updateSchoolYear(SchoolYear schoolYearWithNewValues) {
		if (schoolYearWithNewValues.endDateIsBeforeStartDate()) {
			throw new EndDateBeforeStartDateException();
		}
		SchoolYear SchoolYear = schoolYearRepository.findById(schoolYearWithNewValues.getId());
		SchoolYear.copyValuesFrom(schoolYearWithNewValues);
		schoolYearRepository.save(SchoolYear);
		return SchoolYear.getId();
	}

	@Override
	public void deleteSchoolYear(Long id) {
		// if(SchoolYear.isActuallyUsed(id))
		schoolYearRepository.delete(id);
	}

	@Override
	public SchoolYear removeSchoolYearPart(Long schoolYearId, Long schoolYearPartId) {
		SchoolYear schoolYear = schoolYearRepository.findById(schoolYearId);
		SchoolYearPart part = schoolYearPartRepository.findById(schoolYearPartId);
		schoolYear.removeSchoolYearPart(part);
		schoolYearRepository.save(schoolYear);
		schoolYearPartRepository.delete(part);
		return schoolYear;
	}

	@Override
	public SchoolYear addSchoolYearPart(SchoolYear schoolYear, String name, Date startDate, Date endDate) {
		SchoolYearPart schoolYearPart = new SchoolYearPart(schoolYear, name, startDate, endDate);
		if (schoolYearPart.endDateIsBeforeStartDate()) {
			throw new EndDateBeforeStartDateException();
		}
		schoolYearPartRepository.save(schoolYearPart);
		schoolYear.addSchoolYearPart(schoolYearPart);
		return schoolYearRepository.save(schoolYear);
	}

	@Override
	public SchoolYear editSchoolYearPart(Long id, String name, Date startDate, Date endDate) {
		SchoolYearPart schoolYearPart = schoolYearPartRepository.findById(id);
		SchoolYearPart schoolYearPartWintNewValues = new SchoolYearPart(name, startDate, endDate);
		if (schoolYearPartWintNewValues.endDateIsBeforeStartDate()) {
			throw new EndDateBeforeStartDateException();
		}
		schoolYearPart.copyValuesFrom(schoolYearPartWintNewValues);
		schoolYearPartRepository.save(schoolYearPart);
		return schoolYearRepository.findById(schoolYearPart.getSchoolYearId());
	}

	@Override
	public SchoolYear findOpenedSchoolYear() {
		return schoolYearRepository.findByState(ElementState.Opened);
	}

	@Override
	public void openSchoolYear(Long schoolYearId) {
		SchoolYear openedSchoolYear = schoolYearRepository.findByState(ElementState.Opened);
		if (openedSchoolYear != null && openedSchoolYear.getId() != schoolYearId) {
			throw new DupplicateOpenedSchoolYearException();
		} else {
			SchoolYear schoolYear = schoolYearRepository.findById(schoolYearId);
			schoolYear.setState(ElementState.Opened);
			schoolYearRepository.save(schoolYear);
		}

	}

	@Override
	public void closeSchoolYear(Long schoolYearId) {
		SchoolYear schoolYear = schoolYearRepository.findById(schoolYearId);
		schoolYear.setState(ElementState.Closed);
		schoolYearRepository.save(schoolYear);

	}

	@Override
	public SchoolYear addClassroomToSchoolYear(Long schoolYearId, Long classroomId) {
		SchoolYear schoolYear = schoolYearRepository.findById(schoolYearId);
		Classroom classroom = classroomRepository.findById(classroomId);
		schoolYear.addClassroom(classroom);
		return schoolYearRepository.save(schoolYear);
	}

	@Override
	public SchoolYear removeClassroomToSchoolYear(Long schoolYearId, Long classroomId) {
		SchoolYear schoolYear = schoolYearRepository.findById(schoolYearId);
		Classroom classroom = classroomRepository.findById(classroomId);
		schoolYear.removeClassroom(classroom);
		return schoolYear;
	}

}
