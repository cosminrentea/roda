package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.CmsPageType;


@Service
@Transactional
public class CmsPageTypeServiceImpl implements CmsPageTypeService {

	public long countAllCmsPageTypes() {
        return CmsPageType.countCmsPageTypes();
    }

	public void deleteCmsPageType(CmsPageType cmsPageType) {
        cmsPageType.remove();
    }

	public CmsPageType findCmsPageType(Integer id) {
        return CmsPageType.findCmsPageType(id);
    }

	public List<CmsPageType> findAllCmsPageTypes() {
        return CmsPageType.findAllCmsPageTypes();
    }

	public List<CmsPageType> findCmsPageTypeEntries(int firstResult, int maxResults) {
        return CmsPageType.findCmsPageTypeEntries(firstResult, maxResults);
    }

	public void saveCmsPageType(CmsPageType cmsPageType) {
        cmsPageType.persist();
    }

	public CmsPageType updateCmsPageType(CmsPageType cmsPageType) {
        return cmsPageType.merge();
    }
}
