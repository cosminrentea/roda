package ro.roda.service;

import java.util.List;

import ro.roda.domainjson.CatalogTree;

public interface CatalogTreeService {

	public abstract List<CatalogTree> findAllCatalogTree();

	public abstract CatalogTree findCatalogTree(Integer id);

}
