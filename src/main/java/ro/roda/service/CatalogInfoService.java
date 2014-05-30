package ro.roda.service;

import java.util.List;

import ro.roda.domainjson.CatalogInfo;

public interface CatalogInfoService {

	public abstract List<CatalogInfo> findAllCatalogInfos();

	public abstract CatalogInfo findCatalogInfo(Integer id);

}
