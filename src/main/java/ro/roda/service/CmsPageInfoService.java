package ro.roda.service;

import java.util.List;

import ro.roda.transformer.CmsPageInfo;

public interface CmsPageInfoService {

	public abstract List<CmsPageInfo> findAllCmsPageInfos();

	public abstract CmsPageInfo findCmsPageInfo(Integer id);

}
