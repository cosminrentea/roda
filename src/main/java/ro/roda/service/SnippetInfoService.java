package ro.roda.service;

import java.util.List;

import ro.roda.transformer.SnippetInfo;

public interface SnippetInfoService {

	public abstract List<SnippetInfo> findAllSnippetInfos();

	public abstract SnippetInfo findSnippetInfo(Integer id);

}
