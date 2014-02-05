package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.transformer.CmsPageTree;

@Service
@Transactional
public class CmsPageTreeServiceImpl implements CmsPageTreeService {

	public List<CmsPageTree> findAllCmsPageTrees() {
		return CmsPageTree.findAllCmsPageTrees();
	}

	public CmsPageTree findCmsPageTree(Integer id) {
		return CmsPageTree.findCmsPageTree(id);
	}
}
