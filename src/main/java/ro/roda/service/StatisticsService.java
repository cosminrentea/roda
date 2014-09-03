package ro.roda.service;

import java.util.List;

public interface StatisticsService {

	public abstract String getStatisticsJson(String operation, List<Long> variableIds);

}
