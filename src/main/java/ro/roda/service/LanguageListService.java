package ro.roda.service;

import java.util.List;

import ro.roda.domainjson.LanguageList;

public interface LanguageListService {

	public abstract LanguageList findLanguageList(Integer id);

	public abstract List<LanguageList> findAllLanguageLists();

}
