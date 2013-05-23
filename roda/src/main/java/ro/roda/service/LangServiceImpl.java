package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.Lang;


@Service
@Transactional
public class LangServiceImpl implements LangService {

	public long countAllLangs() {
        return Lang.countLangs();
    }

	public void deleteLang(Lang lang) {
        lang.remove();
    }

	public Lang findLang(Integer id) {
        return Lang.findLang(id);
    }

	public List<Lang> findAllLangs() {
        return Lang.findAllLangs();
    }

	public List<Lang> findLangEntries(int firstResult, int maxResults) {
        return Lang.findLangEntries(firstResult, maxResults);
    }

	public void saveLang(Lang lang) {
        lang.persist();
    }

	public Lang updateLang(Lang lang) {
        return lang.merge();
    }
}
