package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domainjson.LayoutGroupTree;

@Service
@Transactional
public class LayoutGroupTreeServiceImpl implements LayoutGroupTreeService {

	public List<LayoutGroupTree> findAllLayoutGroupTrees() {
		return LayoutGroupTree.findAllLayoutGroupTrees();
	}

	public LayoutGroupTree findLayoutGroupTree(Integer id) {
		return LayoutGroupTree.findLayoutGroupTree(id);
	}
}
