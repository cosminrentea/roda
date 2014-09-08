package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domainjson.QuestionInfo;

@Service
@Transactional
public class QuestionInfoServiceImpl implements QuestionInfoService {

	public List<QuestionInfo> findAllQuestionInfos() {
		return QuestionInfo.findAllQuestionInfos();
	}

	public QuestionInfo findQuestionInfo(Long id) {
		return QuestionInfo.findQuestionInfo(id);
	}
}
