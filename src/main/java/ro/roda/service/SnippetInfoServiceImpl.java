package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domainjson.SnippetInfo;

@Service
@Transactional
public class SnippetInfoServiceImpl implements SnippetInfoService {

	public List<SnippetInfo> findAllSnippetInfos() {
		return SnippetInfo.findAllSnippetInfos();
	}

	public SnippetInfo findSnippetInfo(Integer id) {
		return SnippetInfo.findSnippetInfo(id);
	}
}
