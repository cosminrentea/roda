package ro.roda.service;

import java.util.List;

import ro.roda.domain.Catalog;
import ro.roda.transformer.CatalogByYear;

public interface CatalogByYearService {

	/*
	 * public abstract long countAllCatalogs();
	 * 
	 * @PreAuthorize("hasPermission(#catalog, 'WRITE')") public abstract void
	 * deleteCatalog(Catalog catalog);
	 * 
	 * @PostAuthorize("hasPermission(returnObject, 'WRITE')") public abstract
	 * Catalog findCatalog(Integer id);
	 * 
	 * @PostFilter("hasPermission(filterObject, 'READ')") public abstract
	 * List<Catalog> findAllCatalogs();
	 * 
	 * @PostFilter("hasPermission(filterObject, 'READ')") public abstract
	 * List<Catalog> findCatalogEntries(int firstResult, int maxResults);
	 * 
	 * public abstract void saveCatalog(Catalog catalog);
	 * 
	 * @PreAuthorize("hasPermission(#catalog, 'WRITE')") public abstract Catalog
	 * updateCatalog(Catalog catalog);
	 */

	
	public abstract List<CatalogByYear> findAllCatalogsByYear();
	
	public abstract CatalogByYear findCatalogByYear(Integer id);

	
}
