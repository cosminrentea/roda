package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the person_org database table.
 * 
 */
@Entity
@Table(name="person_org")
public class PersonOrg implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PersonOrgPK id;

	@Column(nullable=false)
	private Timestamp dateend;

	@Column(nullable=false)
	private Timestamp datestart;

	//bi-directional many-to-one association to Org
    @ManyToOne
	@JoinColumn(name="org_id", nullable=false, insertable=false, updatable=false)
	private Org org;

	//bi-directional many-to-one association to Person
    @ManyToOne
	@JoinColumn(name="person_id", nullable=false, insertable=false, updatable=false)
	private Person person;

	//bi-directional many-to-one association to PersonRole
    @ManyToOne
	@JoinColumn(name="role_id", nullable=false)
	private PersonRole personRole;

    public PersonOrg() {
    }

	public PersonOrgPK getId() {
		return this.id;
	}

	public void setId(PersonOrgPK id) {
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

	public Org getOrg() {
		return this.org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}
	
	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
	public PersonRole getPersonRole() {
		return this.personRole;
	}

	public void setPersonRole(PersonRole personRole) {
		this.personRole = personRole;
	}
	
}