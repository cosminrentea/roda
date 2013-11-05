package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.transformer.SnippetGroupInfo;

@Service
@Transactional
public class SnippetGroupInfoServiceImpl implements SnippetGroupInfoService {

	public List<SnippetGroupInfo> findAllSnippetGroupInfos() {
		return SnippetGroupInfo.findAllSnippetGroupInfos();
	}

	public SnippetGroupInfo findSnippetGroupInfo(Integer id) {
		return SnippetGroupInfo.findSnippetGroupInfo(id);
	}
}
