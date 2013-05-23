package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.UnitAnalysis;

public interface UnitAnalysisService {

	public abstract long countAllUnitAnalyses();

	public abstract void deleteUnitAnalysis(UnitAnalysis unitAnalysis);

	public abstract UnitAnalysis findUnitAnalysis(Integer id);

	public abstract List<UnitAnalysis> findAllUnitAnalyses();

	public abstract List<UnitAnalysis> findUnitAnalysisEntries(int firstResult, int maxResults);

	public abstract void saveUnitAnalysis(UnitAnalysis unitAnalysis);

	public abstract UnitAnalysis updateUnitAnalysis(UnitAnalysis unitAnalysis);

}
