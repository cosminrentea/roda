package ro.roda.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domainjson.GeoIndicatorList;

@Service
@Transactional
public class GeoIndicatorListServiceImpl implements GeoIndicatorListService {

	public GeoIndicatorList findGeoIndicatorList(Integer id) {
		return GeoIndicatorList.findGeoIndicatorList(id);
	}

}
