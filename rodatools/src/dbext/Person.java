package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the person database table.
 * 
 */
@Entity
@Table(name = "person")
public class Person implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(nullable = false, length = 100)
	private String fname;

	@Column(nullable = false, length = 100)
	private String lname;

	@Column(nullable = false, length = 100)
	private String mname;

	// bi-directional many-to-one association to Email
	@OneToMany(mappedBy = "person")
	private List<Email> emails;

	// bi-directional many-to-one association to InstancePerson
	@OneToMany(mappedBy = "person")
	private List<InstancePerson> instancePersons;

	// bi-directional many-to-one association to Internet
	@OneToMany(mappedBy = "person")
	private List<Internet> internets;

	// bi-directional many-to-one association to Prefix
	@ManyToOne
	@JoinColumn(name = "prefix_id")
	private Prefix prefix;

	// bi-directional many-to-one association to Suffix
	@ManyToOne
	@JoinColumn(name = "suffix_id")
	private Suffix suffix;

	// bi-directional many-to-one association to PersonAddress
	@OneToMany(mappedBy = "person")
	private List<PersonAddress> personAddresses;

	// bi-directional many-to-one association to PersonOrg
	@OneToMany(mappedBy = "person")
	private List<PersonOrg> personOrgs;

	// bi-directional many-to-one association to StudyPerson
	@OneToMany(mappedBy = "person")
	private List<StudyPerson> studyPersons;

	public Person() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFname() {
		return this.fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return this.lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getMname() {
		return this.mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public List<Email> getEmails() {
		return this.emails;
	}

	public void setEmails(List<Email> emails) {
		this.emails = emails;
	}

	public List<InstancePerson> getInstancePersons() {
		return this.instancePersons;
	}

	public void setInstancePersons(List<InstancePerson> instancePersons) {
		this.instancePersons = instancePersons;
	}

	public List<Internet> getInternets() {
		return this.internets;
	}

	public void setInternets(List<Internet> internets) {
		this.internets = internets;
	}

	public Prefix getPrefix() {
		return this.prefix;
	}

	public void setPrefix(Prefix prefix) {
		this.prefix = prefix;
	}

	public Suffix getSuffix() {
		return this.suffix;
	}

	public void setSuffix(Suffix suffix) {
		this.suffix = suffix;
	}

	public List<PersonAddress> getPersonAddresses() {
		return this.personAddresses;
	}

	public void setPersonAddresses(List<PersonAddress> personAddresses) {
		this.personAddresses = personAddresses;
	}

	public List<PersonOrg> getPersonOrgs() {
		return this.personOrgs;
	}

	public void setPersonOrgs(List<PersonOrg> personOrgs) {
		this.personOrgs = personOrgs;
	}

	public List<StudyPerson> getStudyPersons() {
		return this.studyPersons;
	}

	public void setStudyPersons(List<StudyPerson> studyPersons) {
		this.studyPersons = studyPersons;
	}

}