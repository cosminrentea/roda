package dbext;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the instance_person database table.
 * 
 */
@Entity
@Table(name="instance_person")
public class InstancePerson implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private InstancePersonPK id;

	//bi-directional many-to-one association to Instance
    @ManyToOne
	@JoinColumn(name="instance_id", nullable=false, insertable=false, updatable=false)
	private Instance instance;

	//bi-directional many-to-one association to InstancePersonAssoc
    @ManyToOne
	@JoinColumn(name="assoc_type_id", nullable=false, insertable=false, updatable=false)
	private InstancePersonAssoc instancePersonAssoc;

	//bi-directional many-to-one association to Person
    @ManyToOne
	@JoinColumn(name="person_id", nullable=false, insertable=false, updatable=false)
	private Person person;

    public InstancePerson() {
    }

	public InstancePersonPK getId() {
		return this.id;
	}

	public void setId(InstancePersonPK id) {
		this.id = id;
	}
	
	public Instance getInstance() {
		return this.instance;
	}

	public void setInstance(Instance instance) {
		this.instance = instance;
	}
	
	public InstancePersonAssoc getInstancePersonAssoc() {
		return this.instancePersonAssoc;
	}

	public void setInstancePersonAssoc(InstancePersonAssoc instancePersonAssoc) {
		this.instancePersonAssoc = instancePersonAssoc;
	}
	
	public Person getPerson() {
		return this.person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}
	
}