package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domainjson.DdiEditorSuffixList;

@Service
@Transactional
public class DdiEditorSuffixListServiceImpl implements DdiEditorSuffixListService {

	public List<DdiEditorSuffixList> findAllSuffixes() {
		return DdiEditorSuffixList.findAllSuffixes();
	}

	public DdiEditorSuffixList findSuffix(Integer id) {
		return DdiEditorSuffixList.findSuffix(id);
	}
}
