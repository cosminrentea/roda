package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.SamplingProcedure;

public interface SamplingProcedureService {

	public abstract long countAllSamplingProcedures();

	public abstract void deleteSamplingProcedure(SamplingProcedure samplingProcedure);

	public abstract SamplingProcedure findSamplingProcedure(Integer id);

	public abstract List<SamplingProcedure> findAllSamplingProcedures();

	public abstract List<SamplingProcedure> findSamplingProcedureEntries(int firstResult, int maxResults);

	public abstract void saveSamplingProcedure(SamplingProcedure samplingProcedure);

	public abstract SamplingProcedure updateSamplingProcedure(SamplingProcedure samplingProcedure);

}
