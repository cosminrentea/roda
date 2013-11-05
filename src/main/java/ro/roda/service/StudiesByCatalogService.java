package ro.roda.service;

import java.util.List;

import ro.roda.transformer.StudiesByCatalog;

public interface StudiesByCatalogService {

	public abstract List<StudiesByCatalog> findAllStudiesByCatalog();

	public abstract StudiesByCatalog findStudiesByCatalog(Integer id);

}
