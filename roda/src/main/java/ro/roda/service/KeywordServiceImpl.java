package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.Keyword;

@Service
@Transactional
public class KeywordServiceImpl implements KeywordService {

	public long countAllKeywords() {
		return Keyword.countKeywords();
	}

	public void deleteKeyword(Keyword keyword) {
		keyword.remove();
	}

	public Keyword findKeyword(Integer id) {
		return Keyword.findKeyword(id);
	}

	public List<Keyword> findAllKeywords() {
		return Keyword.findAllKeywords();
	}

	public List<Keyword> findKeywordEntries(int firstResult, int maxResults) {
		return Keyword.findKeywordEntries(firstResult, maxResults);
	}

	public void saveKeyword(Keyword keyword) {
		keyword.persist();
	}

	public Keyword updateKeyword(Keyword keyword) {
		return keyword.merge();
	}
}
