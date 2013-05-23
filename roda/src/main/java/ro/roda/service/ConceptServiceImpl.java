package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.Concept;

@Service
@Transactional
public class ConceptServiceImpl implements ConceptService {

	public long countAllConcepts() {
		return Concept.countConcepts();
	}

	public void deleteConcept(Concept concept) {
		concept.remove();
	}

	public Concept findConcept(Long id) {
		return Concept.findConcept(id);
	}

	public List<Concept> findAllConcepts() {
		return Concept.findAllConcepts();
	}

	public List<Concept> findConceptEntries(int firstResult, int maxResults) {
		return Concept.findConceptEntries(firstResult, maxResults);
	}

	public void saveConcept(Concept concept) {
		concept.persist();
	}

	public Concept updateConcept(Concept concept) {
		return concept.merge();
	}
}
