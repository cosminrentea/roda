package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.Source;

@Service
@Transactional
public class SourceServiceImpl implements SourceService {

	public long countAllSources() {
		return Source.countSources();
	}

	public void deleteSource(Source source) {
		source.remove();
	}

	public Source findSource(Integer id) {
		return Source.findSource(id);
	}

	public List<Source> findAllSources() {
		return Source.findAllSources();
	}

	public List<Source> findSourceEntries(int firstResult, int maxResults) {
		return Source.findSourceEntries(firstResult, maxResults);
	}

	public void saveSource(Source source) {
		source.persist();
	}

	public Source updateSource(Source source) {
		return source.merge();
	}
}
