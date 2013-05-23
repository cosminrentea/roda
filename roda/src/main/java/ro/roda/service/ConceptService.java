package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.Concept;

public interface ConceptService {

	public abstract long countAllConcepts();

	public abstract void deleteConcept(Concept concept);

	public abstract Concept findConcept(Long id);

	public abstract List<Concept> findAllConcepts();

	public abstract List<Concept> findConceptEntries(int firstResult, int maxResults);

	public abstract void saveConcept(Concept concept);

	public abstract Concept updateConcept(Concept concept);

}
