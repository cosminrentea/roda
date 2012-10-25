package dbext;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the person_address database table.
 * 
 */
@Embeddable
public class PersonAddressPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "person_id", unique = true, nullable = false)
	private Integer personId;

	@Column(name = "address_id", unique = true, nullable = false)
	private Integer addressId;

	public PersonAddressPK() {
	}

	public Integer getPersonId() {
		return this.personId;
	}

	public void setPersonId(Integer personId) {
		this.personId = personId;
	}

	public Integer getAddressId() {
		return this.addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PersonAddressPK)) {
			return false;
		}
		PersonAddressPK castOther = (PersonAddressPK) other;
		return this.personId.equals(castOther.personId)
				&& this.addressId.equals(castOther.addressId);

	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.personId.hashCode();
		hash = hash * prime + this.addressId.hashCode();

		return hash;
	}
}