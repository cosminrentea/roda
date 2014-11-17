package ro.roda.service;

import java.util.List;

import ro.roda.domain.GeoDraw;
import ro.roda.domain.GeoDrawPK;

public interface GeoDrawService {

	public abstract long countAllGeoDraws();

	public abstract void deleteGeoDraw(GeoDraw geoDraw);

	public abstract GeoDraw findGeoDraw(GeoDrawPK id);

	public abstract List<GeoDraw> findAllGeoDraws();

	public abstract List<GeoDraw> findGeoDrawEntries(int firstResult, int maxResults);

	public abstract void saveGeoDraw(GeoDraw geoDraw);

	public abstract GeoDraw updateGeoDraw(GeoDraw geoDraw);

}
