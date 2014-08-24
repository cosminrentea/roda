package ro.roda.domainjson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.Org;
import flexjson.JSONSerializer;

@Configurable
public class DdiEditorOrgList extends JsonInfo implements Comparable<DdiEditorOrgList> {

	public static String toJsonArray(Collection<DdiEditorOrgList> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type");
		serializer.include("id", "name");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<DdiEditorOrgList> findAllOrgs() {
		List<DdiEditorOrgList> result = new ArrayList<DdiEditorOrgList>();

		List<Org> orgs = Org.findAllOrgs();

		if (orgs != null && orgs.size() > 0) {

			Iterator<Org> orgsIterator = orgs.iterator();
			while (orgsIterator.hasNext()) {
				Org org = (Org) orgsIterator.next();

				result.add(new DdiEditorOrgList(org));
			}
		}

		return result;
	}

	public static DdiEditorOrgList findOrg(Integer id) {
		Org org = Org.findOrg(id);

		if (org != null) {
			return new DdiEditorOrgList(org);
		}

		return null;
	}

	public DdiEditorOrgList(Integer id, String name) {
		setId(id);
		setName(name);
	}

	public DdiEditorOrgList(Org org) {
		this(org.getId(), org.getShortName());
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type");
		serializer.include("id", "name");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}

	@Override
	public int compareTo(DdiEditorOrgList orgList) {
		return getName().compareTo(orgList.getName());
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getName()).toHashCode();
	}

	@Override
	// TODO Finally, when are equal two orgs?
	public boolean equals(Object other) {
		if (other != null && other instanceof DdiEditorOrgList) {
			return new EqualsBuilder().append(this.getName(), ((DdiEditorOrgList) other).getName()).isEquals();
		} else {
			return false;
		}
	}

}
