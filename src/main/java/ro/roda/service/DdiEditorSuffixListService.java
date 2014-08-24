package ro.roda.service;

import java.util.List;

import ro.roda.domainjson.DdiEditorSuffixList;

public interface DdiEditorSuffixListService {

	public abstract List<DdiEditorSuffixList> findAllSuffixes();

	public abstract DdiEditorSuffixList findSuffix(Integer id);

}
