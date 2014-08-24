package ro.roda.service;

import java.util.List;

import ro.roda.domainjson.DdiEditorPrefixList;

public interface DdiEditorPrefixListService {

	public abstract List<DdiEditorPrefixList> findAllPrefixes();

	public abstract DdiEditorPrefixList findPrefix(Integer id);

}
