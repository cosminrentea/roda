package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.CatalogStudy;
import ro.roda.domain.CatalogStudyPK;

@Service
@Transactional
public class CatalogStudyServiceImpl implements CatalogStudyService {

	public long countAllCatalogStudys() {
		return CatalogStudy.countCatalogStudys();
	}

	public void deleteCatalogStudy(CatalogStudy catalogStudy) {
		catalogStudy.remove();
	}

	public CatalogStudy findCatalogStudy(CatalogStudyPK id) {
		return CatalogStudy.findCatalogStudy(id);
	}

	public List<CatalogStudy> findAllCatalogStudys() {
		return CatalogStudy.findAllCatalogStudys();
	}

	public List<CatalogStudy> findCatalogStudyEntries(int firstResult, int maxResults) {
		return CatalogStudy.findCatalogStudyEntries(firstResult, maxResults);
	}

	public void saveCatalogStudy(CatalogStudy catalogStudy) {
		catalogStudy.persist();
	}

	public CatalogStudy updateCatalogStudy(CatalogStudy catalogStudy) {
		return catalogStudy.merge();
	}
}
