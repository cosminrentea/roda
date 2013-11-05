package ro.roda.service;

import java.util.List;

import ro.roda.domain.CollectionModelType;

public interface CollectionModelTypeService {

	public abstract long countAllCollectionModelTypes();

	public abstract void deleteCollectionModelType(CollectionModelType collectionModelType);

	public abstract CollectionModelType findCollectionModelType(Integer id);

	public abstract List<CollectionModelType> findAllCollectionModelTypes();

	public abstract List<CollectionModelType> findCollectionModelTypeEntries(int firstResult, int maxResults);

	public abstract void saveCollectionModelType(CollectionModelType collectionModelType);

	public abstract CollectionModelType updateCollectionModelType(CollectionModelType collectionModelType);

}
