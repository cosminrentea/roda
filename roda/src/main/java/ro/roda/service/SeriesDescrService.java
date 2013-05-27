package ro.roda.service;

import java.util.List;

import ro.roda.domain.SeriesDescr;
import ro.roda.domain.SeriesDescrPK;

public interface SeriesDescrService {

	public abstract long countAllSeriesDescrs();

	public abstract void deleteSeriesDescr(SeriesDescr seriesDescr);

	public abstract SeriesDescr findSeriesDescr(SeriesDescrPK id);

	public abstract List<SeriesDescr> findAllSeriesDescrs();

	public abstract List<SeriesDescr> findSeriesDescrEntries(int firstResult, int maxResults);

	public abstract void saveSeriesDescr(SeriesDescr seriesDescr);

	public abstract SeriesDescr updateSeriesDescr(SeriesDescr seriesDescr);

}
