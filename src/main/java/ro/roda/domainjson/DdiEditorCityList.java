package ro.roda.domainjson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.City;
import flexjson.JSONSerializer;

@Configurable
public class DdiEditorCityList extends JsonInfo implements Comparable<DdiEditorCityList> {

	public static String toJsonArray(Collection<DdiEditorCityList> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type");
		serializer.include("id", "name");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<DdiEditorCityList> findAllCities() {
		List<DdiEditorCityList> result = new ArrayList<DdiEditorCityList>();

		List<City> cityes = City.findAllCitys();

		if (cityes != null && cityes.size() > 0) {

			Iterator<City> cityesIterator = cityes.iterator();
			while (cityesIterator.hasNext()) {
				City city = (City) cityesIterator.next();

				result.add(new DdiEditorCityList(city));
			}
		}

		return result;
	}

	public static DdiEditorCityList findCity(Integer id) {
		City city = City.findCity(id);

		if (city != null) {
			return new DdiEditorCityList(city);
		}

		return null;
	}

	public DdiEditorCityList(Integer id, String name) {
		setId(id);
		setName(name);
	}

	public DdiEditorCityList(City city) {
		this(city.getId(), city.getName());
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type");
		serializer.include("id", "name");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}

	@Override
	public int compareTo(DdiEditorCityList cityList) {
		return getName().compareTo(cityList.getName());
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getName()).toHashCode();
	}

	@Override
	// TODO Finally, when are equal two persons?
	public boolean equals(Object other) {
		if (other != null && other instanceof DdiEditorCityList) {
			return new EqualsBuilder().append(this.getName(), ((DdiEditorCityList) other).getName()).isEquals();
		} else {
			return false;
		}
	}

}
