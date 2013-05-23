package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.Prefix;

@Service
@Transactional
public class PrefixServiceImpl implements PrefixService {

	public long countAllPrefixes() {
		return Prefix.countPrefixes();
	}

	public void deletePrefix(Prefix prefix) {
		prefix.remove();
	}

	public Prefix findPrefix(Integer id) {
		return Prefix.findPrefix(id);
	}

	public List<Prefix> findAllPrefixes() {
		return Prefix.findAllPrefixes();
	}

	public List<Prefix> findPrefixEntries(int firstResult, int maxResults) {
		return Prefix.findPrefixEntries(firstResult, maxResults);
	}

	public void savePrefix(Prefix prefix) {
		prefix.persist();
	}

	public Prefix updatePrefix(Prefix prefix) {
		return prefix.merge();
	}
}
