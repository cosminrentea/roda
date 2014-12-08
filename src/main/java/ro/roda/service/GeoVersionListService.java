package ro.roda.service;

import java.util.List;

import ro.roda.domainjson.GeoVersionList;

public interface GeoVersionListService {

	public abstract List<GeoVersionList> findAllGeoVersionLists();

	public abstract GeoVersionList findGeoVersionList(Integer id);

}
