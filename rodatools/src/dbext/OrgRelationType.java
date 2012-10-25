package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the org_relation_type database table.
 * 
 */
@Entity
@Table(name = "org_relation_type")
public class OrgRelationType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(nullable = false, length = 50)
	private String name;

	// bi-directional many-to-one association to OrgRelation
	@OneToMany(mappedBy = "orgRelationType")
	private List<OrgRelation> orgRelations;

	public OrgRelationType() {
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

	public List<OrgRelation> getOrgRelations() {
		return this.orgRelations;
	}

	public void setOrgRelations(List<OrgRelation> orgRelations) {
		this.orgRelations = orgRelations;
	}

}