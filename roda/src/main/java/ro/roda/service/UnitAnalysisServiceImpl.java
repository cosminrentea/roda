package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.UnitAnalysis;


@Service
@Transactional
public class UnitAnalysisServiceImpl implements UnitAnalysisService {

	public long countAllUnitAnalyses() {
        return UnitAnalysis.countUnitAnalyses();
    }

	public void deleteUnitAnalysis(UnitAnalysis unitAnalysis) {
        unitAnalysis.remove();
    }

	public UnitAnalysis findUnitAnalysis(Integer id) {
        return UnitAnalysis.findUnitAnalysis(id);
    }

	public List<UnitAnalysis> findAllUnitAnalyses() {
        return UnitAnalysis.findAllUnitAnalyses();
    }

	public List<UnitAnalysis> findUnitAnalysisEntries(int firstResult, int maxResults) {
        return UnitAnalysis.findUnitAnalysisEntries(firstResult, maxResults);
    }

	public void saveUnitAnalysis(UnitAnalysis unitAnalysis) {
        unitAnalysis.persist();
    }

	public UnitAnalysis updateUnitAnalysis(UnitAnalysis unitAnalysis) {
        return unitAnalysis.merge();
    }
}
