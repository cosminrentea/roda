package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domainjson.SnippetTree;

@Service
@Transactional
public class SnippetTreeServiceImpl implements SnippetTreeService {

	public List<SnippetTree> findAllSnippetTrees() {
		return SnippetTree.findAllSnippetTrees();
	}

	public SnippetTree findSnippetTree(Integer id) {
		return SnippetTree.findSnippetGroupTree(id);
	}
}
