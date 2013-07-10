package ro.roda.transformer;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.Catalog;
import flexjson.JSONSerializer;

@Configurable
public class CatalogByYear {

	public static String toJsonArray(Collection<CatalogByYear> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.include("name");
		serializer.include("data", "data.catalogStudies");
		serializer.exclude("*.class", "id", "data.added", "data.parentId");

		return "{\"data\":[{\"name\":\"RODA\",\"level\":0,\"data\":"
				+ serializer.serialize(collection) + "}]}";
	}

	public static List<CatalogByYear> findAllCatalogsByYear() {
		List<CatalogByYear> result = null;
		List<Catalog> catalogs = Catalog
				.entityManager()
				.createQuery(
						"SELECT o FROM Catalog o ORDER BY extract(year from added)",
						Catalog.class).getResultList();
		Set<Catalog> catalogsByYearSet = null;
		Iterator<Catalog> it = catalogs.iterator();
		int yearPrev = 0;

		result = new ArrayList<CatalogByYear>();

		while (it.hasNext()) {
			Catalog catalog = (Catalog) it.next();
			int year = catalog.getAdded().get(Calendar.YEAR);
			if (year != yearPrev || !it.hasNext()) {
				if (!it.hasNext()) {
					catalogsByYearSet.add(catalog);
				}
				if (catalogsByYearSet != null) {
					result.add(new CatalogByYear(String.valueOf(yearPrev),
							catalogsByYearSet));
				}
				catalogsByYearSet = new HashSet<Catalog>();
				yearPrev = year;
			} else {
				catalogsByYearSet.add(catalog);
			}

		}
		return result;
	}

	public static CatalogByYear findCatalogByYear(Integer id) {
		if (id == null)
			return null;
		return new CatalogByYear(Catalog.entityManager()
				.find(Catalog.class, id));
	}

	/*
	 * public static String toJsonArray(Collection<Catalog> collection) {
	 * JSONSerializer serializer = new JSONSerializer();
	 * serializer.exclude("*.class").include("added", "name");
	 * serializer.transform(new BasicDateTransformer(),
	 * Calendar.class).transform(new DateTransformer("yyyy"), "added");
	 * //serializer.transform(new CatalogTransformer("added"), "year"); return
	 * serializer.serialize(collection); }
	 */

	private Integer id;

	private String name;

	private Integer level;

	private Set<Catalog> data;

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String pName) {
		this.name = pName;
	}

	public Set<Catalog> getData() {
		return data;
	}

	public void setData(Set<Catalog> catalogs) {
		this.data = catalogs;
	}

	public CatalogByYear(String pYear) {
		name = pYear;
		data = new HashSet<Catalog>();
	}

	public CatalogByYear(Catalog catalog) {
		this(String.valueOf(catalog.getAdded().get(Calendar.YEAR)));
		data.add(catalog);
	}

	public CatalogByYear(String pYear, Set<Catalog> catalogsByYear) {
		name = pYear;
		data = catalogsByYear;
		level = 1;
		for (Catalog c : catalogsByYear) {
			c.setLevel(2);
		}
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}
}
