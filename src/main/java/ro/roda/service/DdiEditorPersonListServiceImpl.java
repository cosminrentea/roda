package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domainjson.DdiEditorPersonList;

@Service
@Transactional
public class DdiEditorPersonListServiceImpl implements DdiEditorPersonListService {

	public List<DdiEditorPersonList> findAllPersons() {
		return DdiEditorPersonList.findAllPersons();
	}

	public DdiEditorPersonList findPerson(Integer id) {
		return DdiEditorPersonList.findPerson(id);
	}
}
