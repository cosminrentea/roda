package ro.roda.domainjson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.News;
import flexjson.JSONSerializer;
//import java.util.Calendar;

//import ro.roda.domain.CmsSnippetGroup;

@Configurable
public class NewsList extends JsonInfo {

	public static String toJsonArray(Collection<NewsList> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type");
		serializer.include("id", "added", "title", "content", "visible");

		// serializer.transform(new FieldNameTransformer("indice"), "id");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<NewsList> findAllNewsLists() {
		List<NewsList> result = new ArrayList<NewsList>();

		List<News> news = News.findAllNewspieces();

		if (news != null && news.size() > 0) {

			Iterator<News> newsIterator = news.iterator();
			while (newsIterator.hasNext()) {
				News newsitem = (News) newsIterator.next();
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

	private Boolean visible;

	private Date added;

	private String content;

	private String title;

	public NewsList(Integer id, Boolean visible, Date added, String title, String content) {
		this.id = id;
		this.visible = visible;
		this.added = added;
		this.title = title;
		this.content = content;
	}

	public NewsList(News news) {

		this(news.getId(), news.isVisible(), news.getAdded(), news.getTitle(), news.getContent());
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

		serializer.exclude("*.class", "type");
		serializer.include("id", "added", "title", "content", "visible");

		// serializer.transform(new FieldNameTransformer("indice"), "id");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}
}
