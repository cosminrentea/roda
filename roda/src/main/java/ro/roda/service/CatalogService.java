package ro.roda.service;

import java.util.List;

import org.springframework.roo.addon.layers.service.RooService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import ro.roda.domain.Catalog;

@RooService(domainTypes = { ro.roda.domain.Catalog.class })
public interface CatalogService {
	
/*	   
		public abstract long countAllCatalogs();    
	    
		@PreAuthorize("hasPermission(#catalog, 'WRITE')")
		public abstract void deleteCatalog(Catalog catalog);    
	    
		@PostAuthorize("hasPermission(returnObject, 'WRITE')")
		public abstract Catalog findCatalog(Integer id);    
	    
		@PostFilter("hasPermission(filterObject, 'READ')")
		public abstract List<Catalog> findAllCatalogs();    
	    
		@PostFilter("hasPermission(filterObject, 'READ')")
	    public abstract List<Catalog> findCatalogEntries(int firstResult, int maxResults);    
	    
	    public abstract void saveCatalog(Catalog catalog);
	    
		@PreAuthorize("hasPermission(#catalog, 'WRITE')")
	    public abstract Catalog updateCatalog(Catalog catalog);
*/

}
