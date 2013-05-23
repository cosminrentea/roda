package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.Regiontype;


@Service
@Transactional
public class RegiontypeServiceImpl implements RegiontypeService {

	public long countAllRegiontypes() {
        return Regiontype.countRegiontypes();
    }

	public void deleteRegiontype(Regiontype regiontype) {
        regiontype.remove();
    }

	public Regiontype findRegiontype(Integer id) {
        return Regiontype.findRegiontype(id);
    }

	public List<Regiontype> findAllRegiontypes() {
        return Regiontype.findAllRegiontypes();
    }

	public List<Regiontype> findRegiontypeEntries(int firstResult, int maxResults) {
        return Regiontype.findRegiontypeEntries(firstResult, maxResults);
    }

	public void saveRegiontype(Regiontype regiontype) {
        regiontype.persist();
    }

	public Regiontype updateRegiontype(Regiontype regiontype) {
        return regiontype.merge();
    }
}
