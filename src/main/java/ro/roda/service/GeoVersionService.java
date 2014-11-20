package ro.roda.service;

import java.util.List;

import ro.roda.domain.GeoVersion;

public interface GeoVersionService {

	public abstract long countAllGeoVersions();

	public abstract void deleteGeoVersion(GeoVersion geoVersion);

	public abstract GeoVersion findGeoVersion(Integer id);

	public abstract List<GeoVersion> findAllGeoVersions();

	public abstract List<GeoVersion> findGeoVersionEntries(int firstResult, int maxResults);

	public abstract void saveGeoVersion(GeoVersion geoVersion);

	public abstract GeoVersion updateGeoVersion(GeoVersion geoVersion);

}
