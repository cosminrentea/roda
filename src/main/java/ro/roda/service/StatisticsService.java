package ro.roda.service;

import java.util.List;

import ro.roda.domainjson.StatisticsInfo;

public interface StatisticsService {

	public abstract String getStatisticsJson(String operation, Long id1, Long id2);

}
