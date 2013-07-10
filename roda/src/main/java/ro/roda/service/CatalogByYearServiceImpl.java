package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.Catalog;
import ro.roda.transformer.CatalogByYear;

@Service
@Transactional
public class CatalogByYearServiceImpl implements CatalogByYearService {

	public List<CatalogByYear> findAllCatalogsByYear() {
		return CatalogByYear.findAllCatalogsByYear();
	}

	public CatalogByYear findCatalogByYear(Integer id) {
		return CatalogByYear.findCatalogByYear(id);
	}
}
