package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.GeoVersion;

@Service
@Transactional
public class GeoVersionServiceImpl implements GeoVersionService {

	public long countAllGeoVersions() {
		return GeoVersion.countGeoVersions();
	}

	public void deleteGeoVersion(GeoVersion geoVersion) {
		geoVersion.remove();
	}

	public GeoVersion findGeoVersion(Integer id) {
		return GeoVersion.findGeoVersion(id);
	}

	public List<GeoVersion> findAllGeoVersions() {
		return GeoVersion.findAllGeoVersions();
	}

	public List<GeoVersion> findGeoVersionEntries(int firstResult, int maxResults) {
		return GeoVersion.findGeoVersionEntries(firstResult, maxResults);
	}

	public void saveGeoVersion(GeoVersion geoVersion) {
		geoVersion.persist();
	}

	public GeoVersion updateGeoVersion(GeoVersion geoVersion) {
		return geoVersion.merge();
	}
}
