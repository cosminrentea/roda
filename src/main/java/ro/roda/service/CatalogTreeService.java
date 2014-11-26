package ro.roda.service;

import java.util.List;

import ro.roda.domainjson.CatalogTree;

public interface CatalogTreeService {

	public abstract List<CatalogTree> findAll();

	public abstract CatalogTree find(Integer id);

}
