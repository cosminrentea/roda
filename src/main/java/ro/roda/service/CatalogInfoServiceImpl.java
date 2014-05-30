package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domainjson.CatalogInfo;

@Service
@Transactional
public class CatalogInfoServiceImpl implements CatalogInfoService {

	public List<CatalogInfo> findAllCatalogInfos() {
		return CatalogInfo.findAllGroupInfos();
	}

	public CatalogInfo findCatalogInfo(Integer id) {
		return CatalogInfo.findGroupInfo(id);
	}
}
