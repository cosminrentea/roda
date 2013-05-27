package ro.roda.service;

import java.util.List;

import ro.roda.domain.Prefix;

public interface PrefixService {

	public abstract long countAllPrefixes();

	public abstract void deletePrefix(Prefix prefix);

	public abstract Prefix findPrefix(Integer id);

	public abstract List<Prefix> findAllPrefixes();

	public abstract List<Prefix> findPrefixEntries(int firstResult, int maxResults);

	public abstract void savePrefix(Prefix prefix);

	public abstract Prefix updatePrefix(Prefix prefix);

}
