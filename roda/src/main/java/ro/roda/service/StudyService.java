package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.Study;


public interface StudyService {

	public abstract long countAllStudys();


	public abstract void deleteStudy(Study study);


	public abstract Study findStudy(Integer id);


	public abstract List<Study> findAllStudys();


	public abstract List<Study> findStudyEntries(int firstResult, int maxResults);


	public abstract void saveStudy(Study study);


	public abstract Study updateStudy(Study study);

}
