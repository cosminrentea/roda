package dbext;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the setting_values database table.
 * 
 */
@Entity
@Table(name="setting_values")
public class SettingValue implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="setting_id", unique=true, nullable=false)
	private Integer settingId;

	@Column(nullable=false, length=255)
	private String value;

	//bi-directional one-to-one association to Setting
	@OneToOne
	@JoinColumn(name="setting_id", nullable=false, insertable=false, updatable=false)
	private Setting setting;

    public SettingValue() {
    }

	public Integer getSettingId() {
		return this.settingId;
	}

	public void setSettingId(Integer settingId) {
		this.settingId = settingId;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Setting getSetting() {
		return this.setting;
	}

	public void setSetting(Setting setting) {
		this.setting = setting;
	}
	
}