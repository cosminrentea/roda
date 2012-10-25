package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the org_prefix database table.
 * 
 */
@Entity
@Table(name="org_prefix")
public class OrgPrefix implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false, length=255)
	private String description;

	@Column(nullable=false, length=100)
	private String name;

	//bi-directional many-to-one association to Org
	@OneToMany(mappedBy="orgPrefix")
	private List<Org> orgs;

    public OrgPrefix() {
    }

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Org> getOrgs() {
		return this.orgs;
	}

	public void setOrgs(List<Org> orgs) {
		this.orgs = orgs;
	}
	
}