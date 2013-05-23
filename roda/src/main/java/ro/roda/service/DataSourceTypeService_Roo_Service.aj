// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package ro.roda.service;

import java.util.List;
import ro.roda.domain.DataSourceType;
import ro.roda.service.DataSourceTypeService;

privileged aspect DataSourceTypeService_Roo_Service {
    
    public abstract long DataSourceTypeService.countAllDataSourceTypes();    
    public abstract void DataSourceTypeService.deleteDataSourceType(DataSourceType dataSourceType);    
    public abstract DataSourceType DataSourceTypeService.findDataSourceType(Integer id);    
    public abstract List<DataSourceType> DataSourceTypeService.findAllDataSourceTypes();    
    public abstract List<DataSourceType> DataSourceTypeService.findDataSourceTypeEntries(int firstResult, int maxResults);    
    public abstract void DataSourceTypeService.saveDataSourceType(DataSourceType dataSourceType);    
    public abstract DataSourceType DataSourceTypeService.updateDataSourceType(DataSourceType dataSourceType);    
}
