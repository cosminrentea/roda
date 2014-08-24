package ro.roda.domainjson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.Prefix;
import flexjson.JSONSerializer;

@Configurable
public class DdiEditorPrefixList extends JsonInfo implements Comparable<DdiEditorPrefixList> {

	public static String toJsonArray(Collection<DdiEditorPrefixList> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type");
		serializer.include("id", "name");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<DdiEditorPrefixList> findAllPrefixes() {
		List<DdiEditorPrefixList> result = new ArrayList<DdiEditorPrefixList>();

		List<Prefix> prefixes = Prefix.findAllPrefixes();

		if (prefixes != null && prefixes.size() > 0) {

			Iterator<Prefix> prefixesIterator = prefixes.iterator();
			while (prefixesIterator.hasNext()) {
				Prefix prefix = (Prefix) prefixesIterator.next();

				result.add(new DdiEditorPrefixList(prefix));
			}
		}

		return result;
	}

	public static DdiEditorPrefixList findPrefix(Integer id) {
		Prefix prefix = Prefix.findPrefix(id);

		if (prefix != null) {
			return new DdiEditorPrefixList(prefix);
		}

		return null;
	}

	public DdiEditorPrefixList(Integer id, String name) {
		setId(id);
		setName(name);
	}

	public DdiEditorPrefixList(Prefix prefix) {
		this(prefix.getId(), prefix.getName());
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type");
		serializer.include("id", "name");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}

	@Override
	public int compareTo(DdiEditorPrefixList prefixList) {
		return getName().compareTo(prefixList.getName());
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getName()).toHashCode();
	}

	@Override
	// TODO Finally, when are equal two persons?
	public boolean equals(Object other) {
		if (other != null && other instanceof DdiEditorPrefixList) {
			return new EqualsBuilder().append(this.getName(), ((DdiEditorPrefixList) other).getName()).isEquals();
		} else {
			return false;
		}
	}

}
