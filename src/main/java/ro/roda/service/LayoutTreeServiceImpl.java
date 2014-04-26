package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domainjson.LayoutTree;

@Service
@Transactional
public class LayoutTreeServiceImpl implements LayoutTreeService {

	public List<LayoutTree> findAllLayoutTrees() {
		return LayoutTree.findAllLayoutTrees();
	}

	public LayoutTree findLayoutTree(Integer id) {
		return LayoutTree.findLayoutTree(id);
	}
}
