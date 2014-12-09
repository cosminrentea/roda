package ro.roda.domainjson;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.Geography;
import flexjson.JSONSerializer;

@Configurable
public class GeographyInfo extends JsonInfo {

	public static String toJsonArray(Collection<GeographyInfo> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "leaf", "name");

		return serializer.serialize(collection);
	}

	private Integer id;

	private String denloc;

	private Integer siruta;

	private Integer jud;

	public GeographyInfo(Integer id, String denloc, Integer siruta, Integer jud) {
		this.id = id;
		this.denloc = denloc;
		this.siruta = siruta;
		this.jud = jud;
	}

	public GeographyInfo(Geography geography) {
		this(geography.getId(), geography.getDenloc(), geography.getSiruta(), geography.getJud());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDenloc() {
		return denloc;
	}

	public void setDenloc(String denloc) {
		this.denloc = denloc;
	}

	public Integer getSiruta() {
		return siruta;
	}

	public void setSiruta(Integer siruta) {
		this.siruta = siruta;
	}

	public Integer getJud() {
		return jud;
	}

	public void setJud(Integer jud) {
		this.jud = jud;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "leaf", "name");

		return serializer.serialize(this);
	}

}
