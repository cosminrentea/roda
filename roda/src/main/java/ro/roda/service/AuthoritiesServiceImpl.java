package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.Authorities;
import ro.roda.domain.AuthoritiesPK;

@Service
@Transactional
public class AuthoritiesServiceImpl implements AuthoritiesService {

	public long countAllAuthoritieses() {
		return Authorities.countAuthoritieses();
	}

	public void deleteAuthorities(Authorities authorities) {
		authorities.remove();
	}

	public Authorities findAuthorities(AuthoritiesPK id) {
		return Authorities.findAuthorities(id);
	}

	public List<Authorities> findAllAuthoritieses() {
		return Authorities.findAllAuthoritieses();
	}

	public List<Authorities> findAuthoritiesEntries(int firstResult, int maxResults) {
		return Authorities.findAuthoritiesEntries(firstResult, maxResults);
	}

	public void saveAuthorities(Authorities authorities) {
		authorities.persist();
	}

	public Authorities updateAuthorities(Authorities authorities) {
		return authorities.merge();
	}
}
