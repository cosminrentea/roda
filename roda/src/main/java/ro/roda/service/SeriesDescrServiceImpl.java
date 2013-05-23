package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.SeriesDescr;
import ro.roda.domain.SeriesDescrPK;


@Service
@Transactional
public class SeriesDescrServiceImpl implements SeriesDescrService {

	public long countAllSeriesDescrs() {
        return SeriesDescr.countSeriesDescrs();
    }

	public void deleteSeriesDescr(SeriesDescr seriesDescr) {
        seriesDescr.remove();
    }

	public SeriesDescr findSeriesDescr(SeriesDescrPK id) {
        return SeriesDescr.findSeriesDescr(id);
    }

	public List<SeriesDescr> findAllSeriesDescrs() {
        return SeriesDescr.findAllSeriesDescrs();
    }

	public List<SeriesDescr> findSeriesDescrEntries(int firstResult, int maxResults) {
        return SeriesDescr.findSeriesDescrEntries(firstResult, maxResults);
    }

	public void saveSeriesDescr(SeriesDescr seriesDescr) {
        seriesDescr.persist();
    }

	public SeriesDescr updateSeriesDescr(SeriesDescr seriesDescr) {
        return seriesDescr.merge();
    }
}
