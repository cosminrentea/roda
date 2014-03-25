package ro.roda.service;

import java.util.List;

import ro.roda.transformer.CmsPageTypeInfo;

public interface CmsPageTypeInfoService {

	public abstract List<CmsPageTypeInfo> findAllCmsPageTypeInfos();

	public abstract CmsPageTypeInfo findCmsPageTypeInfo(Integer id);
}
