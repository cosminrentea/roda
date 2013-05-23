package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.Keyword;


public interface KeywordService {

	public abstract long countAllKeywords();


	public abstract void deleteKeyword(Keyword keyword);


	public abstract Keyword findKeyword(Integer id);


	public abstract List<Keyword> findAllKeywords();


	public abstract List<Keyword> findKeywordEntries(int firstResult, int maxResults);


	public abstract void saveKeyword(Keyword keyword);


	public abstract Keyword updateKeyword(Keyword keyword);

}
