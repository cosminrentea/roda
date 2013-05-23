package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.News;

@Service
@Transactional
public class NewsServiceImpl implements NewsService {

	public long countAllNewspieces() {
		return News.countNewspieces();
	}

	public void deleteNews(News news) {
		news.remove();
	}

	public News findNews(Integer id) {
		return News.findNews(id);
	}

	public List<News> findAllNewspieces() {
		return News.findAllNewspieces();
	}

	public List<News> findNewsEntries(int firstResult, int maxResults) {
		return News.findNewsEntries(firstResult, maxResults);
	}

	public void saveNews(News news) {
		news.persist();
	}

	public News updateNews(News news) {
		return news.merge();
	}
}
