package dbext;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the org_address database table.
 * 
 */
@Embeddable
public class OrgAddressPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "org_id", unique = true, nullable = false)
	private Integer orgId;

	@Column(name = "address_id", unique = true, nullable = false)
	private Integer addressId;

	public OrgAddressPK() {
	}

	public Integer getOrgId() {
		return this.orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
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
		if (!(other instanceof OrgAddressPK)) {
			return false;
		}
		OrgAddressPK castOther = (OrgAddressPK) other;
		return this.orgId.equals(castOther.orgId)
				&& this.addressId.equals(castOther.addressId);

	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.orgId.hashCode();
		hash = hash * prime + this.addressId.hashCode();

		return hash;
	}
}