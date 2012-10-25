package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the user_settings database table.
 * 
 */
@Entity
@Table(name="user_settings")
public class UserSetting implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(name="default_value", length=255)
	private String defaultValue;

	@Column(length=255)
	private String description;

	@Column(nullable=false, length=150)
	private String name;

	@Column(name="predefined_values", length=255)
	private String predefinedValues;

	//bi-directional many-to-one association to UserSettingValue
	@OneToMany(mappedBy="userSetting")
	private List<UserSettingValue> userSettingValues;

	//bi-directional many-to-one association to UserSettingsGroup
    @ManyToOne
	@JoinColumn(name="setting_group", nullable=false)
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

	public List<UserSettingValue> getUserSettingValues() {
		return this.userSettingValues;
	}

	public void setUserSettingValues(List<UserSettingValue> userSettingValues) {
		this.userSettingValues = userSettingValues;
	}
	
	public UserSettingsGroup getUserSettingsGroup() {
		return this.userSettingsGroup;
	}

	public void setUserSettingsGroup(UserSettingsGroup userSettingsGroup) {
		this.userSettingsGroup = userSettingsGroup;
	}
	
}