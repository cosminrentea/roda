package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domainjson.CatalogTree;

@Service
@Transactional
public class CatalogTreeServiceImpl implements CatalogTreeService {

	public List<CatalogTree> findAllCatalogTree() {
		return CatalogTree.findAllCatalogTree();
	}

	public CatalogTree findCatalogTree(Integer id) {
		return CatalogTree.findCatalogTree(id);
	}
}
