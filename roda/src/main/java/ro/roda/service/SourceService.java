package ro.roda.service;

import java.util.List;

import ro.roda.domain.Source;

public interface SourceService {

	public abstract long countAllSources();

	public abstract void deleteSource(Source source);

	public abstract Source findSource(Integer id);

	public abstract List<Source> findAllSources();

	public abstract List<Source> findSourceEntries(int firstResult, int maxResults);

	public abstract void saveSource(Source source);

	public abstract Source updateSource(Source source);

}
