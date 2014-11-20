package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.GeoDraw;
import ro.roda.domain.GeoDrawPK;

@Service
@Transactional
public class GeoDrawServiceImpl implements GeoDrawService {

	public long countAllGeoDraws() {
		return GeoDraw.countGeoDraws();
	}

	public void deleteGeoDraw(GeoDraw geoDraw) {
		geoDraw.remove();
	}

	public GeoDraw findGeoDraw(GeoDrawPK id) {
		return GeoDraw.findGeoDraw(id);
	}

	public List<GeoDraw> findAllGeoDraws() {
		return GeoDraw.findAllGeoDraws();
	}

	public List<GeoDraw> findGeoDrawEntries(int firstResult, int maxResults) {
		return GeoDraw.findGeoDrawEntries(firstResult, maxResults);
	}

	public void saveGeoDraw(GeoDraw geoDraw) {
		geoDraw.persist();
	}

	public GeoDraw updateGeoDraw(GeoDraw geoDraw) {
		return geoDraw.merge();
	}
}
