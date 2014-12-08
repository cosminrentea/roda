package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domainjson.GeoVersionList;

@Service
@Transactional
public class GeoVersionListServiceImpl implements GeoVersionListService {

	public List<GeoVersionList> findAllGeoVersionLists() {
		return GeoVersionList.findAllGeoVersionLists();
	}

	public GeoVersionList findGeoVersionList(Integer id) {
		return GeoVersionList.findGeoVersionList(id);
	}
}
