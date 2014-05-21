package ro.roda.service;

import java.util.List;

import ro.roda.domainjson.NewsList;

public interface NewsListService {

	public abstract List<NewsList> findAllNewsLists();

	public abstract NewsList findNewsList(Integer id);

}
