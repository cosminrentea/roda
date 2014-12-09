package ro.roda.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domainjson.GeoIndicatorValue;

@Service
@Transactional
public class GeoIndicatorValueServiceImpl implements GeoIndicatorValueService {

	public GeoIndicatorValue findGeoIndicatorValue(Integer geographyId, Integer dataTypeId) {
		return GeoIndicatorValue.findGeoIndicatorValue(geographyId, dataTypeId);
	}

}
