package ro.roda.service;

import java.util.List;

import ro.roda.transformer.StudiesBySeries;

public interface StudiesBySeriesService {

	public abstract List<StudiesBySeries> findAllStudiesBySeries();

	public abstract StudiesBySeries findStudiesBySeries(Integer id);

}
