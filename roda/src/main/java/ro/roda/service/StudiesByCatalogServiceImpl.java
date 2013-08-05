package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.transformer.StudiesByCatalog;

@Service
@Transactional
public class StudiesByCatalogServiceImpl implements StudiesByCatalogService {

	public List<StudiesByCatalog> findAllStudiesByCatalog() {
		return StudiesByCatalog.findAllStudiesByCatalog();
	}

	public StudiesByCatalog findStudiesByCatalog(Integer id) {
		return StudiesByCatalog.findStudiesByCatalog(id);
	}
}
