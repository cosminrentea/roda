package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domainjson.DdiEditorPrefixList;

@Service
@Transactional
public class DdiEditorPrefixListServiceImpl implements DdiEditorPrefixListService {

	public List<DdiEditorPrefixList> findAllPrefixes() {
		return DdiEditorPrefixList.findAllPrefixes();
	}

	public DdiEditorPrefixList findPrefix(Integer id) {
		return DdiEditorPrefixList.findPrefix(id);
	}
}
