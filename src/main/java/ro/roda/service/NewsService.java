package ro.roda.service;

import java.util.List;

import ro.roda.domain.News;

public interface NewsService {

	public abstract long countAllNewspieces();

	public abstract void deleteNews(News news);

	public abstract News findNews(Integer id);

	public abstract List<News> findAllNewspieces();

	public abstract List<News> findNewsEntries(int firstResult, int maxResults);

	public abstract void saveNews(News news);

	public abstract News updateNews(News news);

}
