package ro.roda.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import ro.roda.transformer.CmsPageAccess;
import ro.roda.transformer.CmsPageInfo;

public interface CmsPageInfoService {

	public abstract List<CmsPageInfo> findAllCmsPageInfos();

	public abstract CmsPageInfo findCmsPageInfo(Integer id);

	public abstract List<CmsPageAccess> findAllCmsPageAccesses(HttpServletRequest request);
}
