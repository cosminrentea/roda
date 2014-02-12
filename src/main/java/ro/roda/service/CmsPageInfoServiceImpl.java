package ro.roda.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.transformer.CmsPageAccess;
import ro.roda.transformer.CmsPageInfo;

@Service
@Transactional
public class CmsPageInfoServiceImpl implements CmsPageInfoService {

	public List<CmsPageInfo> findAllCmsPageInfos() {
		return CmsPageInfo.findAllCmsPageInfos();
	}

	public CmsPageInfo findCmsPageInfo(Integer id) {
		return CmsPageInfo.findCmsPageInfo(id);
	}

	public List<CmsPageAccess> findAllCmsPageAccesses(HttpServletRequest request) {
		return CmsPageAccess.findAllCmsPageAccesses(request);
	}
}
