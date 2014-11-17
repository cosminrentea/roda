package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.GeoMargin;

@Service
@Transactional
public class GeoMarginServiceImpl implements GeoMarginService {

	public long countAllGeoMargins() {
		return GeoMargin.countGeoMargins();
	}

	public void deleteGeoMargin(GeoMargin geoMargin) {
		geoMargin.remove();
	}

	public GeoMargin findGeoMargin(Integer id) {
		return GeoMargin.findGeoMargin(id);
	}

	public List<GeoMargin> findAllGeoMargins() {
		return GeoMargin.findAllGeoMargins();
	}

	public List<GeoMargin> findGeoMarginEntries(int firstResult, int maxResults) {
		return GeoMargin.findGeoMarginEntries(firstResult, maxResults);
	}

	public void saveGeoMargin(GeoMargin geoMargin) {
		geoMargin.persist();
	}

	public GeoMargin updateGeoMargin(GeoMargin geoMargin) {
		return geoMargin.merge();
	}
}
