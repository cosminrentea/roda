package ro.roda.service;

import java.util.List;

import ro.roda.domain.Geography;

public interface GeographyService {

	public abstract long countAllGeographys();

	public abstract void deleteGeography(Geography geography);

	public abstract Geography findGeography(Integer id);

	public abstract List<Geography> findAllGeographys();

	public abstract List<Geography> findGeographyEntries(int firstResult, int maxResults);

	public abstract void saveGeography(Geography geography);

	public abstract Geography updateGeography(Geography geography);

}
