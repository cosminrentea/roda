package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.Authorities;
import ro.roda.domain.AuthoritiesPK;


public interface AuthoritiesService {

	public abstract long countAllAuthoritieses();


	public abstract void deleteAuthorities(Authorities authorities);


	public abstract Authorities findAuthorities(AuthoritiesPK id);


	public abstract List<Authorities> findAllAuthoritieses();


	public abstract List<Authorities> findAuthoritiesEntries(int firstResult, int maxResults);


	public abstract void saveAuthorities(Authorities authorities);


	public abstract Authorities updateAuthorities(Authorities authorities);

}
