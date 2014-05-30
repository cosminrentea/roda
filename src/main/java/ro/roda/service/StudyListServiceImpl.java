package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domainjson.StudyList;

@Service
@Transactional
public class StudyListServiceImpl implements StudyListService {

	public List<StudyList> findAllStudyLists() {
		return StudyList.findAllStudyLists();
	}

	public StudyList findStudyList(Integer id) {
		return StudyList.findStudyList(id);
	}
}
