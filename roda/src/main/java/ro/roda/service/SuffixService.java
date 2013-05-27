package ro.roda.service;

import java.util.List;

import ro.roda.domain.Suffix;

public interface SuffixService {

	public abstract long countAllSuffixes();

	public abstract void deleteSuffix(Suffix suffix);

	public abstract Suffix findSuffix(Integer id);

	public abstract List<Suffix> findAllSuffixes();

	public abstract List<Suffix> findSuffixEntries(int firstResult, int maxResults);

	public abstract void saveSuffix(Suffix suffix);

	public abstract Suffix updateSuffix(Suffix suffix);

}
