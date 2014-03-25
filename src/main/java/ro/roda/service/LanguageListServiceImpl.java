package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.transformer.LanguageList;

@Service
@Transactional
public class LanguageListServiceImpl implements LanguageListService {

	public LanguageList findLanguageList(Integer id) {
		return LanguageList.findLanguageList(id);
	}

	public List<LanguageList> findAllLanguageLists() {
		return LanguageList.findAllLanguageLists();
	}
}
