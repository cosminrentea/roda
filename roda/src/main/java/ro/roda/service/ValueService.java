package ro.roda.service;

import java.util.List;

import ro.roda.domain.Value;

public interface ValueService {

	public abstract long countAllValues();

	public abstract void deleteValue(Value value);

	public abstract Value findValue(Long id);

	public abstract List<Value> findAllValues();

	public abstract List<Value> findValueEntries(int firstResult, int maxResults);

	public abstract void saveValue(Value value);

	public abstract Value updateValue(Value value);

}
