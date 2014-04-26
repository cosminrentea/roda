package ro.roda.domainjson;

import java.sql.Date;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.UserProfile;
import flexjson.JSONSerializer;

@Configurable
public class UserProfileInfo extends JsonInfo {

	public static String toJsonArray(Collection<UserProfileInfo> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "name", "id");
		serializer.include("firstname", "lastname", "middlename", "title", "salutation", "image", "address1",
				"address2", "city", "country", "phone", "sex", "birthdate");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	private String salutation;

	private String firstname;

	private String lastname;

	private String middlename;

	private String title;

	private String image;

	private String address1;

	private String address2;

	private String city;

	private String country;

	private String phone;

	private String sex;

	private Date birthdate;

	public UserProfileInfo(String firstname, String lastname, String middlename, String title, String salutation,
			String image, String address1, String address2, String city, String country, String phone, String sex,
			Date birthdate) {
		this.firstname = firstname;
		this.lastname = lastname;
		this.middlename = middlename;
		this.title = title;
		this.salutation = salutation;
		this.image = image;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.country = country;
		this.phone = phone;
		this.sex = sex;
		this.birthdate = birthdate;
	}

	public UserProfileInfo(UserProfile userProfile) {
		this(userProfile.getFirstname(), userProfile.getLastname(), userProfile.getMiddlename(),
				userProfile.getTitle(), userProfile.getSalutation(), userProfile.getImage(), userProfile.getAddress1(),
				userProfile.getAddress2(), userProfile.getCity(), userProfile.getCountry(), userProfile.getPhone(),
				userProfile.getSex(), userProfile.getBirthdate());
	}

	public String getSalutation() {
		return salutation;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getMiddlename() {
		return middlename;
	}

	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "type", "name", "id");
		serializer.include("firstname", "lastname", "middlename", "title", "salutation", "image", "address1",
				"address2", "city", "country", "phone", "sex", "birthdate");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}

	// @Override
	// public int compareTo(UserProfileInfo cmsPageInfo) {
	// // TODO
	// return id < cmsPageInfo.getId() ? -1 : (id > cmsPageInfo.getId() ? 1 :
	// 0);
	// }
	//
	// @Override
	// public boolean equals(Object other) {
	// // TODO
	// if (other != null && other instanceof UserProfileInfo) {
	// return new EqualsBuilder().append(this.getId(), ((UserProfileInfo)
	// other).getId()).isEquals();
	// } else {
	// return false;
	// }
	// }

}
