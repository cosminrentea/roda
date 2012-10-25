package dbext;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the variable_group database table.
 * 
 */
@Entity
@Table(name="variable_group")
public class VariableGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false, length=50)
	private String name;

    public VariableGroup() {
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

}