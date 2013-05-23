package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.CatalogStudy;
import ro.roda.domain.CatalogStudyPK;


public interface CatalogStudyService {

	public abstract long countAllCatalogStudys();


	public abstract void deleteCatalogStudy(CatalogStudy catalogStudy);


	public abstract CatalogStudy findCatalogStudy(CatalogStudyPK id);


	public abstract List<CatalogStudy> findAllCatalogStudys();


	public abstract List<CatalogStudy> findCatalogStudyEntries(int firstResult, int maxResults);


	public abstract void saveCatalogStudy(CatalogStudy catalogStudy);


	public abstract CatalogStudy updateCatalogStudy(CatalogStudy catalogStudy);

}
