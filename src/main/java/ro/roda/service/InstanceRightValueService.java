package ro.roda.service;

import java.util.List;

import ro.roda.domain.InstanceRightValue;

public interface InstanceRightValueService {

	public abstract long countAllInstanceRightValues();

	public abstract void deleteInstanceRightValue(InstanceRightValue instanceRightValue);

	public abstract InstanceRightValue findInstanceRightValue(Integer id);

	public abstract List<InstanceRightValue> findAllInstanceRightValues();

	public abstract List<InstanceRightValue> findInstanceRightValueEntries(int firstResult, int maxResults);

	public abstract void saveInstanceRightValue(InstanceRightValue instanceRightValue);

	public abstract InstanceRightValue updateInstanceRightValue(InstanceRightValue instanceRightValue);

}
