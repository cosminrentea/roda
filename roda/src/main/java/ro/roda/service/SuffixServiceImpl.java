package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.Suffix;

@Service
@Transactional
public class SuffixServiceImpl implements SuffixService {

	public long countAllSuffixes() {
		return Suffix.countSuffixes();
	}

	public void deleteSuffix(Suffix suffix) {
		suffix.remove();
	}

	public Suffix findSuffix(Integer id) {
		return Suffix.findSuffix(id);
	}

	public List<Suffix> findAllSuffixes() {
		return Suffix.findAllSuffixes();
	}

	public List<Suffix> findSuffixEntries(int firstResult, int maxResults) {
		return Suffix.findSuffixEntries(firstResult, maxResults);
	}

	public void saveSuffix(Suffix suffix) {
		suffix.persist();
	}

	public Suffix updateSuffix(Suffix suffix) {
		return suffix.merge();
	}
}
