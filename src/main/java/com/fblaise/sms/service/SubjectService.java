package com.fblaise.sms.service;

import java.util.List;

import com.fblaise.sms.model.Subject;

public interface SubjectService {

	public Subject findSubjectById(Long id);

	public List<Subject> findAllSubjects();

	public Long saveSubject(Subject Subject);

	public Long updateSubject(Subject SubjectWithNewValues);

	public void deleteSubject(Long id);

}