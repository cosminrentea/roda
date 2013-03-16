package ro.roda.service;

import java.util.List;

import org.springframework.roo.addon.layers.service.RooService;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;

import ro.roda.domain.Study;

@RooService(domainTypes = { ro.roda.domain.Study.class })
public interface StudyService {

    public abstract long countAllStudys();    
    
	@PreAuthorize("hasPermission(#study, 'WRITE')")
    public abstract void deleteStudy(Study study);    
	
	@PostAuthorize("hasPermission(returnObject, 'WRITE')")
    public abstract Study findStudy(Integer id);    
	
	@PostFilter("hasPermission(filterObject, 'READ')")
    public abstract List<Study> findAllStudys();    
	
	@PostFilter("hasPermission(filterObject, 'READ')")
    public abstract List<Study> findStudyEntries(int firstResult, int maxResults);    
	
    public abstract void saveStudy(Study study);    
    
	@PreAuthorize("hasPermission(#study, 'WRITE')")
    public abstract Study updateStudy(Study study);    
	
}
