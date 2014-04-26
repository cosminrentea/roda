package ro.roda.service;

import java.util.List;

import ro.roda.domainjson.StudiesBySeries;

public interface StudiesBySeriesService {

	public abstract List<StudiesBySeries> findAllStudiesBySeries();

	public abstract StudiesBySeries findStudiesBySeries(Integer id);

}
