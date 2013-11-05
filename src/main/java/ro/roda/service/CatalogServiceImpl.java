package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.Catalog;

@Service
@Transactional
public class CatalogServiceImpl implements CatalogService {

	public long countAllCatalogs() {
		return Catalog.countCatalogs();
	}

	public void deleteCatalog(Catalog catalog) {
		catalog.remove();
	}

	public Catalog findCatalog(Integer id) {
		return Catalog.findCatalog(id);
	}

	public List<Catalog> findAllCatalogs() {
		return Catalog.findAllCatalogs();
	}

	public List<Catalog> findCatalogEntries(int firstResult, int maxResults) {
		return Catalog.findCatalogEntries(firstResult, maxResults);
	}

	public void saveCatalog(Catalog catalog) {
		catalog.persist();
	}

	public Catalog updateCatalog(Catalog catalog) {
		return catalog.merge();
	}
}
