// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.CollectionModelType;
import ro.roda.service.CollectionModelTypeServiceImpl;

privileged aspect CollectionModelTypeServiceImpl_Roo_Service {
    
    declare @type: CollectionModelTypeServiceImpl: @Service;
    
    declare @type: CollectionModelTypeServiceImpl: @Transactional;
    
    public long CollectionModelTypeServiceImpl.countAllCollectionModelTypes() {
        return CollectionModelType.countCollectionModelTypes();
    }
    
    public void CollectionModelTypeServiceImpl.deleteCollectionModelType(CollectionModelType collectionModelType) {
        collectionModelType.remove();
    }
    
    public CollectionModelType CollectionModelTypeServiceImpl.findCollectionModelType(Integer id) {
        return CollectionModelType.findCollectionModelType(id);
    }
    
    public List<CollectionModelType> CollectionModelTypeServiceImpl.findAllCollectionModelTypes() {
        return CollectionModelType.findAllCollectionModelTypes();
    }
    
    public List<CollectionModelType> CollectionModelTypeServiceImpl.findCollectionModelTypeEntries(int firstResult, int maxResults) {
        return CollectionModelType.findCollectionModelTypeEntries(firstResult, maxResults);
    }
    
    public void CollectionModelTypeServiceImpl.saveCollectionModelType(CollectionModelType collectionModelType) {
        collectionModelType.persist();
    }
    
    public CollectionModelType CollectionModelTypeServiceImpl.updateCollectionModelType(CollectionModelType collectionModelType) {
        return collectionModelType.merge();
    }
    
}