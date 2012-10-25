package dbext;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the user_settings database table.
 * 
 */
@Entity
@Table(name = "user_settings")
public class UserSetting implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(name = "default_value")
	private String defaultValue;

	private String description;

	@Column(nullable = false, length = 150)
	private String name;

	@Column(name = "predefined_values")
	private String predefinedValues;

	// bi-directional one-to-one association to UserSettingValue
	@OneToOne(mappedBy = "userSetting")
	private UserSettingValue userSettingValue;

	// bi-directional many-to-one association to UserSettingsGroup
	@ManyToOne
	@JoinColumn(name = "setting_group", nullable = false)
	private UserSettingsGroup userSettingsGroup;

	public UserSetting() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDefaultValue() {
		return this.defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
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

	public String getPredefinedValues() {
		return this.predefinedValues;
	}

	public void setPredefinedValues(String predefinedValues) {
		this.predefinedValues = predefinedValues;
	}

	public UserSettingValue getUserSettingValue() {
		return this.userSettingValue;
	}

	public void setUserSettingValue(UserSettingValue userSettingValue) {
		this.userSettingValue = userSettingValue;
	}

	public UserSettingsGroup getUserSettingsGroup() {
		return this.userSettingsGroup;
	}

	public void setUserSettingsGroup(UserSettingsGroup userSettingsGroup) {
		this.userSettingsGroup = userSettingsGroup;
	}

}