package com.fblaise.sms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fblaise.sms.model.Subject;
import com.fblaise.sms.repository.SubjectRepository;

@Service("subjectServiceImpl")
public class SubjectServiceImpl implements SubjectService {

	@Autowired
	private SubjectRepository subjectRepository;

	@Override
	public Subject findSubjectById(Long id) {
		return subjectRepository.findById(id).get();
	}

	@Override
	public List<Subject> findAllSubjects() {
		return subjectRepository.findAll();
	}

	@Override
	public Long saveSubject(Subject subject) {
		subject = subjectRepository.save(subject);
		return subject.getId();
	}

	@Override
	public Long updateSubject(Subject subjectWithNewValues) {
		Subject Subject = subjectRepository.findById(subjectWithNewValues.getId()).get();
		Subject.copyValuesFrom(subjectWithNewValues);
		subjectRepository.save(Subject);
		return Subject.getId();
	}

	@Override
	public void deleteSubject(Long id) {
		subjectRepository.deleteById(id);
	}

}
