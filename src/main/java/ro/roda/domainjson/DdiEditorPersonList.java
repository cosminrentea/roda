package ro.roda.domainjson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.Person;
import flexjson.JSONSerializer;

@Configurable
public class DdiEditorPersonList extends JsonInfo implements Comparable<DdiEditorPersonList> {

	public static String toJsonArray(Collection<DdiEditorPersonList> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type");
		serializer.include("id", "name");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<DdiEditorPersonList> findAllPersons() {
		List<DdiEditorPersonList> result = new ArrayList<DdiEditorPersonList>();

		List<Person> persons = Person.findAllPeople();

		if (persons != null && persons.size() > 0) {

			Iterator<Person> personsIterator = persons.iterator();
			while (personsIterator.hasNext()) {
				Person person = (Person) personsIterator.next();

				result.add(new DdiEditorPersonList(person));
			}
		}

		return result;
	}

	public static DdiEditorPersonList findPerson(Integer id) {
		Person person = Person.findPerson(id);

		if (person != null) {
			return new DdiEditorPersonList(person);
		}

		return null;
	}

	public DdiEditorPersonList(Integer id, String name) {
		setId(id);
		setName(name);
	}

	public DdiEditorPersonList(Person person) {
		this(person.getId(), person.getFname() + " " + person.getLname());
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type");
		serializer.include("id", "name");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}

	@Override
	public int compareTo(DdiEditorPersonList personList) {
		return getName().compareTo(personList.getName());
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getName()).toHashCode();
	}

	@Override
	// TODO Finally, when are equal two persons?
	public boolean equals(Object other) {
		if (other != null && other instanceof DdiEditorPersonList) {
			return new EqualsBuilder().append(this.getName(), ((DdiEditorPersonList) other).getName()).isEquals();
		} else {
			return false;
		}
	}

}
