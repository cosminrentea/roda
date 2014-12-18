package ro.roda.domainjson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.Country;
import flexjson.JSONSerializer;

@Configurable
public class DdiEditorCountryList extends JsonInfo implements Comparable<DdiEditorCountryList> {

	public static String toJsonArray(Collection<DdiEditorCountryList> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type");
		serializer.include("id", "name");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<DdiEditorCountryList> findAllCountries() {
		List<DdiEditorCountryList> result = new ArrayList<DdiEditorCountryList>();

		List<Country> countries = Country.findAllCountrys();

		if (countries != null && countries.size() > 0) {

			Iterator<Country> countriesIterator = countries.iterator();
			while (countriesIterator.hasNext()) {
				Country country = (Country) countriesIterator.next();

				result.add(new DdiEditorCountryList(country));
			}
		}

		return result;
	}

	public static DdiEditorCountryList findCountry(Integer id) {
		Country country = Country.findCountry(id);

		if (country != null) {
			return new DdiEditorCountryList(country);
		}

		return null;
	}

	public DdiEditorCountryList(Integer id, String name) {
		setId(id);
		setName(name);
	}

	public DdiEditorCountryList(Country country, Integer lang) {
		onCreateDdiCountry(country, lang);
	}

	public DdiEditorCountryList(Country country) {
		this(country, 2); // default EN
	}

	private void onCreateDdiCountry(Country country, Integer lang) {
		// TODO manage the language better
		String name = "";
		if (lang == 2) { // EN
			name = country.getNameEn();
		} else {
			name = country.getNameRo();
		}

		setId(country.getId());
		setName(name);
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type");
		serializer.include("id", "name");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}

	@Override
	public int compareTo(DdiEditorCountryList countryList) {
		return getName().compareTo(countryList.getName());
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(getName()).toHashCode();
	}

	@Override
	// TODO Finally, when are equal two persons?
	public boolean equals(Object other) {
		if (other != null && other instanceof DdiEditorCountryList) {
			return new EqualsBuilder().append(this.getName(), ((DdiEditorCountryList) other).getName()).isEquals();
		} else {
			return false;
		}
	}

}
