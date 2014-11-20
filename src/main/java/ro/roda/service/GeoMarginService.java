package ro.roda.service;

import java.util.List;

import ro.roda.domain.GeoMargin;

public interface GeoMarginService {

	public abstract long countAllGeoMargins();

	public abstract void deleteGeoMargin(GeoMargin geoMargin);

	public abstract GeoMargin findGeoMargin(Integer id);

	public abstract List<GeoMargin> findAllGeoMargins();

	public abstract List<GeoMargin> findGeoMarginEntries(int firstResult, int maxResults);

	public abstract void saveGeoMargin(GeoMargin geoMargin);

	public abstract GeoMargin updateGeoMargin(GeoMargin geoMargin);

}
