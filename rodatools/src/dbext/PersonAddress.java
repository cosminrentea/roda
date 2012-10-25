package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * The persistent class for the person_address database table.
 * 
 */
@Entity
@Table(name = "person_address")
public class PersonAddress implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PersonAddressPK id;

	@Column(nullable = false)
	private Timestamp dateend;

	@Column(nullable = false)
	private Timestamp datestart;

	// bi-directional many-to-one association to Address
	@ManyToOne
	@JoinColumn(name = "address_id", nullable = false, insertable = false, updatable = false)
	private Address address;

	// bi-directional many-to-one association to Person
	@ManyToOne
	@JoinColumn(name = "person_id", nullable = false, insertable = false, updatable = false)
	private Person person;

	public PersonAddress() {
	}

	public PersonAddressPK getId() {
		return this.id;
	}

	public void setId(PersonAddressPK id) {
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

	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

}