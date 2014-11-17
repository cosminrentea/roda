package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.GeoDatatype;

@Service
@Transactional
public class GeoDatatypeServiceImpl implements GeoDatatypeService {

	public long countAllGeoDatatypes() {
		return GeoDatatype.countGeoDatatypes();
	}

	public void deleteGeoDatatype(GeoDatatype geoDatatype) {
		geoDatatype.remove();
	}

	public GeoDatatype findGeoDatatype(Integer id) {
		return GeoDatatype.findGeoDatatype(id);
	}

	public List<GeoDatatype> findAllGeoDatatypes() {
		return GeoDatatype.findAllGeoDatatypes();
	}

	public List<GeoDatatype> findGeoDatatypeEntries(int firstResult, int maxResults) {
		return GeoDatatype.findGeoDatatypeEntries(firstResult, maxResults);
	}

	public void saveGeoDatatype(GeoDatatype geoDatatype) {
		geoDatatype.persist();
	}

	public GeoDatatype updateGeoDatatype(GeoDatatype geoDatatype) {
		return geoDatatype.merge();
	}
}
