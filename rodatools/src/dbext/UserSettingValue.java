package dbext;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the user_setting_value database table.
 * 
 */
@Entity
@Table(name = "user_setting_value")
public class UserSettingValue implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UserSettingValuePK id;

	@Column(nullable = false)
	private String value;

	// bi-directional one-to-one association to UserSetting
	@OneToOne
	@JoinColumn(name = "user_setting_id", nullable = false, insertable = false, updatable = false)
	private UserSetting userSetting;

	// bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name = "userdb_id", nullable = false, insertable = false, updatable = false)
	private Userdb userdb;

	public UserSettingValue() {
	}

	public UserSettingValuePK getId() {
		return this.id;
	}

	public void setId(UserSettingValuePK id) {
		this.id = id;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public UserSetting getUserSetting() {
		return this.userSetting;
	}

	public void setUserSetting(UserSetting userSetting) {
		this.userSetting = userSetting;
	}

	public Userdb getUserdb() {
		return this.userdb;
	}

	public void setUserdb(Userdb userdb) {
		this.userdb = userdb;
	}

}