package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.SamplingProcedure;

@Service
@Transactional
public class SamplingProcedureServiceImpl implements SamplingProcedureService {

	public long countAllSamplingProcedures() {
		return SamplingProcedure.countSamplingProcedures();
	}

	public void deleteSamplingProcedure(SamplingProcedure samplingProcedure) {
		samplingProcedure.remove();
	}

	public SamplingProcedure findSamplingProcedure(Integer id) {
		return SamplingProcedure.findSamplingProcedure(id);
	}

	public List<SamplingProcedure> findAllSamplingProcedures() {
		return SamplingProcedure.findAllSamplingProcedures();
	}

	public List<SamplingProcedure> findSamplingProcedureEntries(int firstResult, int maxResults) {
		return SamplingProcedure.findSamplingProcedureEntries(firstResult, maxResults);
	}

	public void saveSamplingProcedure(SamplingProcedure samplingProcedure) {
		samplingProcedure.persist();
	}

	public SamplingProcedure updateSamplingProcedure(SamplingProcedure samplingProcedure) {
		return samplingProcedure.merge();
	}
}
