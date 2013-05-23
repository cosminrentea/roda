package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.Vargroup;


public interface VargroupService {

	public abstract long countAllVargroups();


	public abstract void deleteVargroup(Vargroup vargroup);


	public abstract Vargroup findVargroup(Long id);


	public abstract List<Vargroup> findAllVargroups();


	public abstract List<Vargroup> findVargroupEntries(int firstResult, int maxResults);


	public abstract void saveVargroup(Vargroup vargroup);


	public abstract Vargroup updateVargroup(Vargroup vargroup);

}
