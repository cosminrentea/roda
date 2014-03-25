package ro.roda.transformer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.Lang;
import flexjson.JSONSerializer;

@Configurable
public class LanguageList extends JsonInfo {

	public static String toJsonArray(Collection<LanguageList> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "name");
		serializer.include("id", "iso639", "nameEn", "nameRo", "nameSelf");

		serializer.transform(new FieldNameTransformer("indice"), "id");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<LanguageList> findAllLanguageLists() {
		List<LanguageList> result = new ArrayList<LanguageList>();

		List<Lang> langs = Lang.findAllLangs();

		if (langs != null && langs.size() > 0) {

			Iterator<Lang> langsIterator = langs.iterator();
			while (langsIterator.hasNext()) {
				Lang lang = (Lang) langsIterator.next();
				result.add(new LanguageList(lang));
			}
		}

		return result;
	}

	public static LanguageList findLanguageList(Integer id) {
		Lang lang = Lang.findLang(id);

		if (lang != null) {
			return new LanguageList(lang);
		}
		return null;
	}

	private String iso639;

	private String nameEn;

	private String nameRo;

	private String nameSelf;

	public LanguageList(Integer id, String iso639, String nameEn, String nameRo, String nameSelf) {
		setId(id);
		setIso639(iso639);
		setNameEn(nameEn);
		setNameRo(nameRo);
		setNameSelf(nameSelf);
	}

	public LanguageList(Lang lang) {
		this(lang.getId(), lang.getIso639(), lang.getNameEn(), lang.getNameRo(), lang.getNameSelf());
	}

	public String getIso639() {
		return iso639;
	}

	public void setIso639(String iso639) {
		this.iso639 = iso639;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public String getNameRo() {
		return nameRo;
	}

	public void setNameRo(String nameRo) {
		this.nameRo = nameRo;
	}

	public String getNameSelf() {
		return nameSelf;
	}

	public void setNameSelf(String nameSelf) {
		this.nameSelf = nameSelf;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "name");
		serializer.exclude("*.class", "type");
		serializer.include("id", "iso639", "nameEn", "nameRo", "nameSelf");

		serializer.transform(new FieldNameTransformer("indice"), "id");
		return "{\"data\":" + serializer.serialize(this) + "}";
	}
}
