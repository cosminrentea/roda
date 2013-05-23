package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.Value;

@Service
@Transactional
public class ValueServiceImpl implements ValueService {

	public long countAllValues() {
		return Value.countValues();
	}

	public void deleteValue(Value value) {
		value.remove();
	}

	public Value findValue(Long id) {
		return Value.findValue(id);
	}

	public List<Value> findAllValues() {
		return Value.findAllValues();
	}

	public List<Value> findValueEntries(int firstResult, int maxResults) {
		return Value.findValueEntries(firstResult, maxResults);
	}

	public void saveValue(Value value) {
		value.persist();
	}

	public Value updateValue(Value value) {
		return value.merge();
	}
}
