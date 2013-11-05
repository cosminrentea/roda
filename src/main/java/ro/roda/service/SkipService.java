package ro.roda.service;

import java.util.List;

import ro.roda.domain.Skip;

public interface SkipService {

	public abstract long countAllSkips();

	public abstract void deleteSkip(Skip skip);

	public abstract Skip findSkip(Long id);

	public abstract List<Skip> findAllSkips();

	public abstract List<Skip> findSkipEntries(int firstResult, int maxResults);

	public abstract void saveSkip(Skip skip);

	public abstract Skip updateSkip(Skip skip);

}
