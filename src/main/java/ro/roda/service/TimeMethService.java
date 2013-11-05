package ro.roda.service;

import java.util.List;

import ro.roda.domain.TimeMeth;

public interface TimeMethService {

	public abstract long countAllTimeMeths();

	public abstract void deleteTimeMeth(TimeMeth timeMeth);

	public abstract TimeMeth findTimeMeth(Integer id);

	public abstract List<TimeMeth> findAllTimeMeths();

	public abstract List<TimeMeth> findTimeMethEntries(int firstResult,
			int maxResults);

	public abstract void saveTimeMeth(TimeMeth timeMeth);

	public abstract TimeMeth updateTimeMeth(TimeMeth timeMeth);

}
