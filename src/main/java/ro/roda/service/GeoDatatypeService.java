package ro.roda.service;

import java.util.List;

import ro.roda.domain.GeoDatatype;

public interface GeoDatatypeService {

	public abstract long countAllGeoDatatypes();

	public abstract void deleteGeoDatatype(GeoDatatype geoDatatype);

	public abstract GeoDatatype findGeoDatatype(Integer id);

	public abstract List<GeoDatatype> findAllGeoDatatypes();

	public abstract List<GeoDatatype> findGeoDatatypeEntries(int firstResult, int maxResults);

	public abstract void saveGeoDatatype(GeoDatatype geoDatatype);

	public abstract GeoDatatype updateGeoDatatype(GeoDatatype geoDatatype);

}
