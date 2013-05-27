package ro.roda.service;

import java.util.List;

import ro.roda.domain.Internet;

public interface InternetService {

	public abstract long countAllInternets();

	public abstract void deleteInternet(Internet internet);

	public abstract Internet findInternet(Integer id);

	public abstract List<Internet> findAllInternets();

	public abstract List<Internet> findInternetEntries(int firstResult, int maxResults);

	public abstract void saveInternet(Internet internet);

	public abstract Internet updateInternet(Internet internet);

}
