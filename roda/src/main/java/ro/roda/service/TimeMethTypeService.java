package ro.roda.service;

import java.util.List;

import ro.roda.domain.TimeMethType;

public interface TimeMethTypeService {

	public abstract long countAllTimeMethTypes();

	public abstract void deleteTimeMethType(TimeMethType timeMethType);

	public abstract TimeMethType findTimeMethType(Integer id);

	public abstract List<TimeMethType> findAllTimeMethTypes();

	public abstract List<TimeMethType> findTimeMethTypeEntries(int firstResult, int maxResults);

	public abstract void saveTimeMethType(TimeMethType timeMethType);

	public abstract TimeMethType updateTimeMethType(TimeMethType timeMethType);

}
