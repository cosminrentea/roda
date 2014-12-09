package ro.roda.service;

import ro.roda.domainjson.GeoIndicatorValue;

public interface GeoIndicatorValueService {

	public abstract GeoIndicatorValue findGeoIndicatorValue(Integer geographyId, Integer dataTypeId);

}
