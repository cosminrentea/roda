package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.TimeMethType;

@Service
@Transactional
public class TimeMethTypeServiceImpl implements TimeMethTypeService {

	public long countAllTimeMethTypes() {
		return TimeMethType.countTimeMethTypes();
	}

	public void deleteTimeMethType(TimeMethType timeMethType) {
		timeMethType.remove();
	}

	public TimeMethType findTimeMethType(Integer id) {
		return TimeMethType.findTimeMethType(id);
	}

	public List<TimeMethType> findAllTimeMethTypes() {
		return TimeMethType.findAllTimeMethTypes();
	}

	public List<TimeMethType> findTimeMethTypeEntries(int firstResult, int maxResults) {
		return TimeMethType.findTimeMethTypeEntries(firstResult, maxResults);
	}

	public void saveTimeMethType(TimeMethType timeMethType) {
		timeMethType.persist();
	}

	public TimeMethType updateTimeMethType(TimeMethType timeMethType) {
		return timeMethType.merge();
	}
}
