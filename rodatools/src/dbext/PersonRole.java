package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the person_role database table.
 * 
 */
@Entity
@Table(name="person_role")
public class PersonRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false, length=250)
	private String name;

	//bi-directional many-to-one association to PersonOrg
	@OneToMany(mappedBy="personRole")
	private List<PersonOrg> personOrgs;

    public PersonRole() {
    }

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PersonOrg> getPersonOrgs() {
		return this.personOrgs;
	}

	public void setPersonOrgs(List<PersonOrg> personOrgs) {
		this.personOrgs = personOrgs;
	}
	
}