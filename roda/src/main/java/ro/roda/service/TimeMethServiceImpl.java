package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.TimeMeth;

@Service
@Transactional
public class TimeMethServiceImpl implements TimeMethService {

	public long countAllTimeMeths() {
		return TimeMeth.countTimeMeths();
	}

	public void deleteTimeMeth(TimeMeth timeMethType) {
		timeMethType.remove();
	}

	public TimeMeth findTimeMeth(Integer id) {
		return TimeMeth.findTimeMeth(id);
	}

	public List<TimeMeth> findAllTimeMeths() {
		return TimeMeth.findAllTimeMeths();
	}

	public List<TimeMeth> findTimeMethEntries(int firstResult, int maxResults) {
		return TimeMeth.findTimeMethEntries(firstResult, maxResults);
	}

	public void saveTimeMeth(TimeMeth timeMethType) {
		timeMethType.persist();
	}

	public TimeMeth updateTimeMeth(TimeMeth timeMethType) {
		return timeMethType.merge();
	}
}
