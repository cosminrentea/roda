package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.GeoData;
import ro.roda.domain.GeoDataPK;

@Service
@Transactional
public class GeoDataServiceImpl implements GeoDataService {

	public long countAllGeoDatas() {
		return GeoData.countGeoDatas();
	}

	public void deleteGeoData(GeoData geoData) {
		geoData.remove();
	}

	public GeoData findGeoData(GeoDataPK id) {
		return GeoData.findGeoData(id);
	}

	public List<GeoData> findAllGeoDatas() {
		return GeoData.findAllGeoData();
	}

	public List<GeoData> findGeoDataEntries(int firstResult, int maxResults) {
		return GeoData.findGeoDataEntries(firstResult, maxResults);
	}

	public void saveGeoData(GeoData geoData) {
		geoData.persist();
	}

	public GeoData updateGeoData(GeoData geoData) {
		return geoData.merge();
	}
}
