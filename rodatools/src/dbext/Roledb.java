package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the role database table.
 * 
 */
@Entity
@Table(name = "roledb")
public class Roledb implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false, length = 150)
	private String name;

	// bi-directional many-to-many association to User
	@ManyToMany(mappedBy = "roledb")
	private List<Userdb> userdb;

	public Roledb() {
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

	public List<Userdb> getUserdb() {
		return this.userdb;
	}

	public void setUserdb(List<Userdb> userdb) {
		this.userdb = userdb;
	}

}