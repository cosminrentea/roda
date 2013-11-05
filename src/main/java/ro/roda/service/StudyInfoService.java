package ro.roda.service;

import java.util.List;

import ro.roda.transformer.StudyInfo;

public interface StudyInfoService {

	public abstract List<StudyInfo> findAllStudyInfos();
	
	public abstract StudyInfo findStudyInfo(Integer id);

	
}
