package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.OtherStatistic;


public interface OtherStatisticService {

	public abstract long countAllOtherStatistics();


	public abstract void deleteOtherStatistic(OtherStatistic otherStatistic);


	public abstract OtherStatistic findOtherStatistic(Long id);


	public abstract List<OtherStatistic> findAllOtherStatistics();


	public abstract List<OtherStatistic> findOtherStatisticEntries(int firstResult, int maxResults);


	public abstract void saveOtherStatistic(OtherStatistic otherStatistic);


	public abstract OtherStatistic updateOtherStatistic(OtherStatistic otherStatistic);

}
