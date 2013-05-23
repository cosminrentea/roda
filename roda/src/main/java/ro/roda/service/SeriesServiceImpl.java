package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.Series;

@Service
@Transactional
public class SeriesServiceImpl implements SeriesService {

	public long countAllSerieses() {
		return Series.countSerieses();
	}

	public void deleteSeries(Series series) {
		series.remove();
	}

	public Series findSeries(Integer id) {
		return Series.findSeries(id);
	}

	public List<Series> findAllSerieses() {
		return Series.findAllSerieses();
	}

	public List<Series> findSeriesEntries(int firstResult, int maxResults) {
		return Series.findSeriesEntries(firstResult, maxResults);
	}

	public void saveSeries(Series series) {
		series.persist();
	}

	public Series updateSeries(Series series) {
		return series.merge();
	}
}
