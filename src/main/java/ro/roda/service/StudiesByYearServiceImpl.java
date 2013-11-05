package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.transformer.StudiesByYear;

@Service
@Transactional
public class StudiesByYearServiceImpl implements StudiesByYearService {

	public List<StudiesByYear> findAllStudiesByYear() {
		return StudiesByYear.findAllStudiesByYear();
	}

	public StudiesByYear findStudiesByYear(Integer year) {
		return StudiesByYear.findStudiesByYear(year);
	}
}
