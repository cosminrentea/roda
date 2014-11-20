package ro.roda.service;

import java.util.List;

import ro.roda.domain.GeoData;
import ro.roda.domain.GeoDataPK;

public interface GeoDataService {

	public abstract long countAllGeoDatas();

	public abstract void deleteGeoData(GeoData geoData);

	public abstract GeoData findGeoData(GeoDataPK id);

	public abstract List<GeoData> findAllGeoDatas();

	public abstract List<GeoData> findGeoDataEntries(int firstResult, int maxResults);

	public abstract void saveGeoData(GeoData geoData);

	public abstract GeoData updateGeoData(GeoData geoData);

}
