package ro.roda.service;

import java.util.List;

import ro.roda.domainjson.QuestionInfo;

public interface QuestionInfoService {

	public abstract List<QuestionInfo> findAllQuestionInfos();

	public abstract QuestionInfo findQuestionInfo(Long id);

}
