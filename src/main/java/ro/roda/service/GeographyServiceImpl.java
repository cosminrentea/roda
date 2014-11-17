package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.Geography;

@Service
@Transactional
public class GeographyServiceImpl implements GeographyService {

	public long countAllGeographys() {
		return Geography.countGeographys();
	}

	public void deleteGeography(Geography geography) {
		geography.remove();
	}

	public Geography findGeography(Integer id) {
		return Geography.findGeography(id);
	}

	public List<Geography> findAllGeographys() {
		return Geography.findAllGeographys();
	}

	public List<Geography> findGeographyEntries(int firstResult, int maxResults) {
		return Geography.findGeographyEntries(firstResult, maxResults);
	}

	public void saveGeography(Geography geography) {
		geography.persist();
	}

	public Geography updateGeography(Geography geography) {
		return geography.merge();
	}
}
