package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.transformer.StudiesBySeries;

@Service
@Transactional
public class StudiesBySeriesServiceImpl implements StudiesBySeriesService {

	public List<StudiesBySeries> findAllStudiesBySeries() {
		return StudiesBySeries.findAllStudiesBySeries();
	}

	public StudiesBySeries findStudiesBySeries(Integer id) {
		return StudiesBySeries.findStudiesBySeries(id);
	}
}
