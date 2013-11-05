package ro.roda.service;

import java.util.List;

import ro.roda.domain.Regiontype;

public interface RegiontypeService {

	public abstract long countAllRegiontypes();

	public abstract void deleteRegiontype(Regiontype regiontype);

	public abstract Regiontype findRegiontype(Integer id);

	public abstract List<Regiontype> findAllRegiontypes();

	public abstract List<Regiontype> findRegiontypeEntries(int firstResult, int maxResults);

	public abstract void saveRegiontype(Regiontype regiontype);

	public abstract Regiontype updateRegiontype(Regiontype regiontype);

}
