package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.Series;

public interface SeriesService {

	public abstract long countAllSerieses();

	public abstract void deleteSeries(Series series);

	public abstract Series findSeries(Integer id);

	public abstract List<Series> findAllSerieses();

	public abstract List<Series> findSeriesEntries(int firstResult, int maxResults);

	public abstract void saveSeries(Series series);

	public abstract Series updateSeries(Series series);

}
