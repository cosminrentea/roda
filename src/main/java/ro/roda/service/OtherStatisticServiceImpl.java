package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.OtherStatistic;

@Service
@Transactional
public class OtherStatisticServiceImpl implements OtherStatisticService {

	public long countAllOtherStatistics() {
		return OtherStatistic.countOtherStatistics();
	}

	public void deleteOtherStatistic(OtherStatistic otherStatistic) {
		otherStatistic.remove();
	}

	public OtherStatistic findOtherStatistic(Long id) {
		return OtherStatistic.findOtherStatistic(id);
	}

	public List<OtherStatistic> findAllOtherStatistics() {
		return OtherStatistic.findAllOtherStatistics();
	}

	public List<OtherStatistic> findOtherStatisticEntries(int firstResult, int maxResults) {
		return OtherStatistic.findOtherStatisticEntries(firstResult, maxResults);
	}

	public void saveOtherStatistic(OtherStatistic otherStatistic) {
		otherStatistic.persist();
	}

	public OtherStatistic updateOtherStatistic(OtherStatistic otherStatistic) {
		return otherStatistic.merge();
	}
}
