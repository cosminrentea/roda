package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.Skip;


@Service
@Transactional
public class SkipServiceImpl implements SkipService {

	public long countAllSkips() {
        return Skip.countSkips();
    }

	public void deleteSkip(Skip skip) {
        skip.remove();
    }

	public Skip findSkip(Long id) {
        return Skip.findSkip(id);
    }

	public List<Skip> findAllSkips() {
        return Skip.findAllSkips();
    }

	public List<Skip> findSkipEntries(int firstResult, int maxResults) {
        return Skip.findSkipEntries(firstResult, maxResults);
    }

	public void saveSkip(Skip skip) {
        skip.persist();
    }

	public Skip updateSkip(Skip skip) {
        return skip.merge();
    }
}
