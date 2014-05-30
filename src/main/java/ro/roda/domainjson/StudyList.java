package ro.roda.domainjson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.Catalog;
import ro.roda.domain.Study;
import ro.roda.transformer.FieldNameTransformer;
import flexjson.JSONSerializer;

@Configurable
public class StudyList extends JsonInfo implements Comparable<StudyList> {

	public static String toJsonArray(Collection<StudyList> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "leaf");
		serializer.include("id", "name", "groupid", "itemtype", "description");

		serializer.transform(new FieldNameTransformer("indice"), "id");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<StudyList> findAllStudyLists() {
		List<StudyList> result = new ArrayList<StudyList>();

		List<Study> studys = Study.findAllStudys();

		if (studys != null && studys.size() > 0) {

			Iterator<Study> studysIterator = studys.iterator();
			while (studysIterator.hasNext()) {
				Study study = (Study) studysIterator.next();

				// Integer groupId = study.getCatalogId() == null ? null
				// : study.getCatalogId().getId();

				// result.add(new StudyList(study.getId(), study.getName(),
				// study.getPages().size(), groupId,
				// "study", getStudyPath(study), study.getDescription()));
				result.add(new StudyList(study));
			}
		}

		return result;
	}

	// public static String getStudyPath(Study study) {
	// if (study.getCatalogStudies()getCatalogId() != null) {
	// return getCatalogPath(study.getCatalogId());
	// } else {
	// return null; // study.getName();
	// }
	// }

	public static String getCatalogPath(Catalog catalog) {
		if (catalog.getParentId() != null) {
			return getCatalogPath(catalog.getParentId()) + "/" + catalog.getName();
		} else {
			return catalog.getName();
		}
	}

	public static StudyList findStudyList(Integer id) {
		Study study = Study.findStudy(id);

		if (study != null) {
			// return new StudyList(study.getId(), study.getName(),
			// study.getPages().size(),
			// study.getCatalogId() != null ?
			// study.getCatalogId().getId() : null, "study",
			// getStudyPath(study), study.getDescription());
			return new StudyList(study);
		}
		// the below code is commented due to the fact that the Study List
		// should contain only studys, not study groups
		/*
		 * else { Catalog catalog = Catalog.findCatalog(id);
		 * 
		 * if (catalog != null) { return new StudyList(catalog.getId(),
		 * catalog.getName(), getCatalogPagesNumber(catalog),
		 * study.getCatalogId() != null ? study .getCatalogId().getId() : null,
		 * "studygroup", getCatalogPath(catalog), catalog.getDescription()); } }
		 */
		return null;
	}

	private Integer id;

	private String name;

	private Integer groupid;

	private String itemtype;

	private String description;

	private boolean leaf = true;

	public StudyList(Integer id, String name, Integer groupId, String itemtype, String description) {
		this.id = id;
		this.name = name;
		this.itemtype = itemtype;
		this.description = description;
	}

	public StudyList(Study study) {
		this(study.getId(), study.getStudyDescrs().iterator().next().getTitle(),
				study.getCatalogStudies() == null ? null : study.getCatalogStudies().iterator().next().getCatalogId()
						.getId(), "study", study.getStudyDescrs().iterator().next().getAbstract1());
	}

	public StudyList(Catalog catalog) {
		this(catalog.getId(), catalog.getName(), catalog.getParentId() == null ? null : catalog.getParentId().getId(),
				"catalog", catalog.getDescription());

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

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGroupid() {
		return groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	public String getItemtype() {
		return itemtype;
	}

	public void setItemtype(String itemtype) {
		this.itemtype = itemtype;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isLeaf() {
		return leaf;
	}

	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "leaf");
		serializer.include("id", "name", "groupid", "itemtype", "directory", "description");

		serializer.transform(new FieldNameTransformer("indice"), "id");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}

	@Override
	public int compareTo(StudyList studyList) {
		// System.out.println("Compare " + ((itemtype.equals("study") ? "2" :
		// "1") + " " + name + " " + groupid)
		// + (studyList.getItemtype().equals("study") ? "2" : "1") + " " +
		// studyList.getName() + " "
		// + studyList.getGroupid());
		return ((itemtype.equals("study") ? "2" : "1") + " " + name + " " + groupid).compareTo((studyList.getItemtype()
				.equals("study") ? "2" : "1") + " " + studyList.getName() + " " + studyList.getGroupid());
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(itemtype == null ? 0 : (itemtype.equals("catalog") ? 1 : 2))
				.append(groupid == null ? 0 : groupid.intValue()).append(name).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other != null && other instanceof StudyList) {
			return new EqualsBuilder().append(this.getItemtype(), ((StudyList) other).getItemtype())
					.append(this.getGroupid(), ((StudyList) other).getGroupid())
					.append(this.getName(), ((StudyList) other).getName()).isEquals();
		} else {
			return false;
		}
	}

}
