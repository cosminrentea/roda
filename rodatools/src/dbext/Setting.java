package dbext;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the setting database table.
 * 
 */
@Entity
@Table(name="setting")
public class Setting implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(name="default_value", nullable=false, length=255)
	private String defaultValue;

	@Column(nullable=false, length=255)
	private String description;

	@Column(nullable=false, length=150)
	private String name;

	@Column(name="predefined_values", nullable=false, length=255)
	private String predefinedValues;

	//bi-directional many-to-one association to SettingGroup
    @ManyToOne
	@JoinColumn(name="setting_group", nullable=false)
	private SettingGroup settingGroupBean;

	//bi-directional one-to-one association to SettingValue
	@OneToOne(mappedBy="setting")
	private SettingValue settingValue;

    public Setting() {
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

	public SettingGroup getSettingGroupBean() {
		return this.settingGroupBean;
	}

	public void setSettingGroupBean(SettingGroup settingGroupBean) {
		this.settingGroupBean = settingGroupBean;
	}
	
	public SettingValue getSettingValue() {
		return this.settingValue;
	}

	public void setSettingValue(SettingValue settingValue) {
		this.settingValue = settingValue;
	}
	
}