package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.transformer.CmsMenuTree;

@Service
@Transactional
public class CmsMenuTreeServiceImpl implements CmsMenuTreeService {

	public List<CmsMenuTree> findAllCmsMenuTrees() {
		return CmsMenuTree.findAllCmsMenuTree();
	}

	public CmsMenuTree findCmsMenuTree(Integer id) {
		return CmsMenuTree.findCmsMenuTree(id);
	}
}
