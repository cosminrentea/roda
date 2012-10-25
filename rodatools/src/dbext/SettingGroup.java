package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the setting_group database table.
 * 
 */
@Entity
@Table(name = "setting_group")
public class SettingGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false, length = 150)
	private String name;

	@Column(nullable = false)
	private Integer parent;

	// bi-directional many-to-one association to Setting
	@OneToMany(mappedBy = "settingGroupBean")
	private List<Setting> settings;

	public SettingGroup() {
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

	public Integer getParent() {
		return this.parent;
	}

	public void setParent(Integer parent) {
		this.parent = parent;
	}

	public List<Setting> getSettings() {
		return this.settings;
	}

	public void setSettings(List<Setting> settings) {
		this.settings = settings;
	}

}