package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.transformer.StudyInfo;

@Service
@Transactional
public class StudyInfoServiceImpl implements StudyInfoService {

	public List<StudyInfo> findAllStudyInfos() {
		return StudyInfo.findAllStudyInfos();
	}

	public StudyInfo findStudyInfo(Integer id) {
		return StudyInfo.findStudyInfo(id);
	}
}
