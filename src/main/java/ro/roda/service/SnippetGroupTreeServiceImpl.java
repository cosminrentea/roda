package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domainjson.SnippetGroupTree;

@Service
@Transactional
public class SnippetGroupTreeServiceImpl implements SnippetGroupTreeService {

	public List<SnippetGroupTree> findAllSnippetGroupTrees() {
		return SnippetGroupTree.findAllSnippetGroupTrees();
	}

	public SnippetGroupTree findSnippetGroupTree(Integer id) {
		return SnippetGroupTree.findSnippetGroupTree(id);
	}
}
