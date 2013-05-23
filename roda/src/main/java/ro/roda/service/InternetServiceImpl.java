package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.Internet;


@Service
@Transactional
public class InternetServiceImpl implements InternetService {

	public long countAllInternets() {
        return Internet.countInternets();
    }

	public void deleteInternet(Internet internet) {
        internet.remove();
    }

	public Internet findInternet(Integer id) {
        return Internet.findInternet(id);
    }

	public List<Internet> findAllInternets() {
        return Internet.findAllInternets();
    }

	public List<Internet> findInternetEntries(int firstResult, int maxResults) {
        return Internet.findInternetEntries(firstResult, maxResults);
    }

	public void saveInternet(Internet internet) {
        internet.persist();
    }

	public Internet updateInternet(Internet internet) {
        return internet.merge();
    }
}
