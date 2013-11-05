package ro.roda.service;

import java.util.List;

import ro.roda.domain.Lang;

public interface LangService {

	public abstract long countAllLangs();

	public abstract void deleteLang(Lang lang);

	public abstract Lang findLang(Integer id);

	public abstract List<Lang> findAllLangs();

	public abstract List<Lang> findLangEntries(int firstResult, int maxResults);

	public abstract void saveLang(Lang lang);

	public abstract Lang updateLang(Lang lang);

}
