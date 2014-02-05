package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
