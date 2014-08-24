package ro.roda.domainjson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.Suffix;
import flexjson.JSONSerializer;

@Configurable
public class DdiEditorSuffixList extends JsonInfo implements Comparable<DdiEditorSuffixList> {

	public static String toJsonArray(Collection<DdiEditorSuffixList> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type");
		serializer.include("id", "name");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<DdiEditorSuffixList> findAllSuffixes() {
		List<DdiEditorSuffixList> result = new ArrayList<DdiEditorSuffixList>();

		List<Suffix> suffixes = Suffix.findAllSuffixes();

		if (suffixes != null && suffixes.size() > 0) {

			Iterator<Suffix> suffixesIterator = suffixes.iterator();
			while (suffixesIterator.hasNext()) {
				Suffix suffix = (Suffix) suffixesIterator.next();

				result.add(new DdiEditorSuffixList(suffix));
			}
		}

		return result;
	}

	public static DdiEditorSuffixList findSuffix(Integer id) {
		Suffix suffix = Suffix.findSuffix(id);

		if (suffix != null) {
			return new DdiEditorSuffixList(suffix);
		}

		return null;
	}

	public DdiEditorSuffixList(Integer id, String name) {
		setId(id);
		setName(name);
	}

	public DdiEditorSuffixList(Suffix suffix) {
		this(suffix.getId(), suffix.getName());
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type");
		serializer.include("id", "name");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}

	@Override
	public int compareTo(DdiEditorSuffixList suffixList) {
		return getName().compareTo(suffixList.getName());
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getName()).toHashCode();
	}

	@Override
	// TODO Finally, when are equal two persons?
	public boolean equals(Object other) {
		if (other != null && other instanceof DdiEditorSuffixList) {
			return new EqualsBuilder().append(this.getName(), ((DdiEditorSuffixList) other).getName()).isEquals();
		} else {
			return false;
		}
	}

}
