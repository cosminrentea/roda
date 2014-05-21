package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domainjson.NewsList;

@Service
@Transactional
public class NewsListServiceImpl implements NewsListService {

	public List<NewsList> findAllNewsLists() {
		return NewsList.findAllNewsLists();
	}

	public NewsList findNewsList(Integer id) {
		return NewsList.findNewsList(id);
	}
}
