package ro.roda.service;

import ro.roda.domainjson.GeographyByVersion;

public interface GeographyByVersionService {

	public abstract GeographyByVersion findGeographiesByVersion(Integer id);

}
