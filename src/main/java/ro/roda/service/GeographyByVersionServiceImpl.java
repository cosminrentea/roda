package ro.roda.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domainjson.GeographyByVersion;

@Service
@Transactional
public class GeographyByVersionServiceImpl implements GeographyByVersionService {

	public GeographyByVersion findGeographiesByVersion(Integer id) {
		return GeographyByVersion.findGeographiesByVersion(id);
	}

}
