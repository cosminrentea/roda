package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.transformer.CmsPageTypeInfo;

@Service
@Transactional
public class CmsPageTypeInfoServiceImpl implements CmsPageTypeInfoService {

	public List<CmsPageTypeInfo> findAllCmsPageTypeInfos() {
		return CmsPageTypeInfo.findAllCmsPageTypeInfos();
	}

	public CmsPageTypeInfo findCmsPageTypeInfo(Integer id) {
		return CmsPageTypeInfo.findCmsPageTypeInfo(id);
	}

}
