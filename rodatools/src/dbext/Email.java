package dbext;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the emails database table.
 * 
 */
@Entity
@Table(name="emails")
public class Email implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false, length=200)
	private String email;

	@Column(nullable=false)
	private Boolean ismain;

	//bi-directional many-to-one association to Org
    @ManyToOne
	@JoinColumn(name="entity_type", nullable=false)
	private Org org;

	//bi-directional many-to-one association to Person
    @ManyToOne
	@JoinColumn(name="entity_id", nullable=false)
	private Person person;

    public Email() {
    }

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getIsmain() {
		return this.ismain;
	}

	public void setIsmain(Boolean ismain) {
		this.ismain = ismain;
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
	
}