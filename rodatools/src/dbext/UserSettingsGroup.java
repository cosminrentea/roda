package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the user_settings_group database table.
 * 
 */
@Entity
@Table(name = "user_settings_group")
public class UserSettingsGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(unique = true, nullable = false)
	private Integer id;

	private String description;

	@Column(nullable = false, length = 150)
	private String name;

	// bi-directional many-to-one association to UserSetting
	@OneToMany(mappedBy = "userSettingsGroup")
	private List<UserSetting> userSettings;

	public UserSettingsGroup() {
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

	public List<UserSetting> getUserSettings() {
		return this.userSettings;
	}

	public void setUserSettings(List<UserSetting> userSettings) {
		this.userSettings = userSettings;
	}

}