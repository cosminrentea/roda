package ro.roda.domainjson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.News;
import flexjson.JSONSerializer;

@Configurable
public class NewsList extends JsonInfo {

	public static String toJsonArray(Collection<NewsList> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "name");
		serializer.include("id", "added", "title", "content", "visible", "langId", "langCode");

		// serializer.transform(new FieldNameTransformer("indice"), "id");
		serializer.transform(DATE_TRANSFORMER, "added");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<NewsList> findAllNewsLists() {
		List<NewsList> result = new ArrayList<NewsList>();

		List<News> news = News.findAllNewspieces();

		if (news != null && news.size() > 0) {

			Iterator<News> newsIterator = news.iterator();
			while (newsIterator.hasNext()) {
				News newsitem = (News) newsIterator.next();

				Integer langId = (newsitem.getLangId() == null) ? null : newsitem.getLangId().getId();
				String langCode = (newsitem.getLangId() == null) ? null : newsitem.getLangId().getIso639();
				result.add(new NewsList(newsitem));
			}
		}

		return result;
	}

	public static NewsList findNewsList(Integer id) {
		News newsitem = News.findNews(id);

		if (newsitem != null) {
			return new NewsList(newsitem);
		}
		return null;
	}

	private Integer id;

	private Integer langId;

	private String langCode;

	private Boolean visible;

	private Date added;

	private String content;

	private String title;

	public NewsList(Integer id, Boolean visible, Date added, String title, String content, Integer langId,
			String langCode) {
		this.id = id;
		this.visible = visible;
		this.added = added;
		this.title = title;
		this.content = content;
		this.langId = langId;
		this.langCode = langCode;
	}

	public NewsList(News news) {

		this(news.getId(), news.isVisible(), news.getAdded(), news.getTitle(), news.getContent(),
				news.getLangId() == null ? null : news.getLangId().getId(), news.getLangId() == null ? null : news
						.getLangId().getIso639());
		// this.content = news.getContent();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getAdded() {
		return added;
	}

	public void setAdded(Date added) {
		this.added = added;
	}

	public Boolean getVisible() {
		return visible;
	}

	public Integer getLangId() {
		return langId;
	}

	public void setLangId(Integer langId) {
		this.langId = langId;
	}

	public String getLangCode() {
		return langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "name");
		serializer.include("id", "added", "title", "content", "visible", "langId", "langCode");

		// serializer.transform(new FieldNameTransformer("indice"), "id");
		serializer.transform(DATE_TRANSFORMER, "added");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}
}
