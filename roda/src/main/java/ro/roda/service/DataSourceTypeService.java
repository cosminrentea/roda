package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.DataSourceType;


public interface DataSourceTypeService {

	public abstract long countAllDataSourceTypes();


	public abstract void deleteDataSourceType(DataSourceType dataSourceType);


	public abstract DataSourceType findDataSourceType(Integer id);


	public abstract List<DataSourceType> findAllDataSourceTypes();


	public abstract List<DataSourceType> findDataSourceTypeEntries(int firstResult, int maxResults);


	public abstract void saveDataSourceType(DataSourceType dataSourceType);


	public abstract DataSourceType updateDataSourceType(DataSourceType dataSourceType);

}
