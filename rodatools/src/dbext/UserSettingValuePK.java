package dbext;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the user_setting_value database table.
 * 
 */
@Embeddable
public class UserSettingValuePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="user_id", unique=true, nullable=false)
	private Integer userId;

	@Column(name="user_setting_id", unique=true, nullable=false)
	private Integer userSettingId;

    public UserSettingValuePK() {
    }
	public Integer getUserId() {
		return this.userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public Integer getUserSettingId() {
		return this.userSettingId;
	}
	public void setUserSettingId(Integer userSettingId) {
		this.userSettingId = userSettingId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UserSettingValuePK)) {
			return false;
		}
		UserSettingValuePK castOther = (UserSettingValuePK)other;
		return 
			this.userId.equals(castOther.userId)
			&& this.userSettingId.equals(castOther.userSettingId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.userId.hashCode();
		hash = hash * prime + this.userSettingId.hashCode();
		
		return hash;
    }
}