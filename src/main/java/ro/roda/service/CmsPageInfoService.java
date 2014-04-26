package ro.roda.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ro.roda.domainjson.CmsPageAccess;
import ro.roda.domainjson.CmsPageInfo;

public interface CmsPageInfoService {

	public abstract List<CmsPageInfo> findAllCmsPageInfos();

	public abstract CmsPageInfo findCmsPageInfo(Integer id);

	public abstract List<CmsPageAccess> findAllCmsPageAccesses(HttpServletRequest request);
}
