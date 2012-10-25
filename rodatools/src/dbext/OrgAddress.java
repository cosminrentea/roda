package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the org_address database table.
 * 
 */
@Entity
@Table(name = "org_address")
public class OrgAddress implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private OrgAddressPK id;

	@Column(nullable = false)
	private Timestamp dateend;

	@Column(nullable = false)
	private Timestamp datestart;

	// bi-directional many-to-one association to Address
	@ManyToOne
	@JoinColumn(name = "address_id", nullable = false, insertable = false, updatable = false)
	private Address address;

	// bi-directional one-to-one association to Org
	@OneToOne
	@JoinColumn(name = "org_id", nullable = false, insertable = false, updatable = false)
	private Org org;

	public OrgAddress() {
	}

	public OrgAddressPK getId() {
		return this.id;
	}

	public void setId(OrgAddressPK id) {
		this.id = id;
	}

	public Timestamp getDateend() {
		return this.dateend;
	}

	public void setDateend(Timestamp dateend) {
		this.dateend = dateend;
	}

	public Timestamp getDatestart() {
		return this.datestart;
	}

	public void setDatestart(Timestamp datestart) {
		this.datestart = datestart;
	}

	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Org getOrg() {
		return this.org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

}