package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domainjson.SnippetList;

@Service
@Transactional
public class SnippetListServiceImpl implements SnippetListService {

	public List<SnippetList> findAllSnippetLists() {
		return SnippetList.findAllSnippetLists();
	}

	public SnippetList findSnippetList(Integer id) {
		return SnippetList.findSnippetList(id);
	}
}
