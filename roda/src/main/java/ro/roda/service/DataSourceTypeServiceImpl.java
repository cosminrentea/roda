package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.DataSourceType;

@Service
@Transactional
public class DataSourceTypeServiceImpl implements DataSourceTypeService {

	public long countAllDataSourceTypes() {
		return DataSourceType.countDataSourceTypes();
	}

	public void deleteDataSourceType(DataSourceType dataSourceType) {
		dataSourceType.remove();
	}

	public DataSourceType findDataSourceType(Integer id) {
		return DataSourceType.findDataSourceType(id);
	}

	public List<DataSourceType> findAllDataSourceTypes() {
		return DataSourceType.findAllDataSourceTypes();
	}

	public List<DataSourceType> findDataSourceTypeEntries(int firstResult, int maxResults) {
		return DataSourceType.findDataSourceTypeEntries(firstResult, maxResults);
	}

	public void saveDataSourceType(DataSourceType dataSourceType) {
		dataSourceType.persist();
	}

	public DataSourceType updateDataSourceType(DataSourceType dataSourceType) {
		return dataSourceType.merge();
	}
}
