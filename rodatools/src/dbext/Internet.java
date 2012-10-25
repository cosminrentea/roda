package dbext;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the internet database table.
 * 
 */
@Entity
@Table(name="internet")
public class Internet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false, length=250)
	private String content;

	@Column(name="entity_type", nullable=false)
	private Integer entityType;

	@Column(name="internet_type_id", nullable=false)
	private Integer internetTypeId;

	//bi-directional many-to-one association to Org
    @ManyToOne
	@JoinColumn(name="org_id", nullable=false)
	private Org org;

	//bi-directional many-to-one association to Person
    @ManyToOne
	@JoinColumn(name="person_id", nullable=false)
	private Person person;

    public Internet() {
    }

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getEntityType() {
		return this.entityType;
	}

	public void setEntityType(Integer entityType) {
		this.entityType = entityType;
	}

	public Integer getInternetTypeId() {
		return this.internetTypeId;
	}

	public void setInternetTypeId(Integer internetTypeId) {
		this.internetTypeId = internetTypeId;
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