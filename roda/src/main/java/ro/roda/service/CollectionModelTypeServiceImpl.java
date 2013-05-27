package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.CollectionModelType;

@Service
@Transactional
public class CollectionModelTypeServiceImpl implements CollectionModelTypeService {

	public long countAllCollectionModelTypes() {
		return CollectionModelType.countCollectionModelTypes();
	}

	public void deleteCollectionModelType(CollectionModelType collectionModelType) {
		collectionModelType.remove();
	}

	public CollectionModelType findCollectionModelType(Integer id) {
		return CollectionModelType.findCollectionModelType(id);
	}

	public List<CollectionModelType> findAllCollectionModelTypes() {
		return CollectionModelType.findAllCollectionModelTypes();
	}

	public List<CollectionModelType> findCollectionModelTypeEntries(int firstResult, int maxResults) {
		return CollectionModelType.findCollectionModelTypeEntries(firstResult, maxResults);
	}

	public void saveCollectionModelType(CollectionModelType collectionModelType) {
		collectionModelType.persist();
	}

	public CollectionModelType updateCollectionModelType(CollectionModelType collectionModelType) {
		return collectionModelType.merge();
	}
}
