package ro.roda.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.beans.factory.annotation.Configurable;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

@Embeddable
@Configurable
public final class UserSettingValuePK implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Collection<UserSettingValuePK> fromJsonArrayToUserSettingValuePKs(String json) {
		return new JSONDeserializer<List<UserSettingValuePK>>().use(null, ArrayList.class)
				.use("values", UserSettingValuePK.class).deserialize(json);
	}

	public static UserSettingValuePK fromJsonToUserSettingValuePK(String json) {
		return new JSONDeserializer<UserSettingValuePK>().use(null, UserSettingValuePK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<UserSettingValuePK> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@Column(name = "user_id", columnDefinition = "int4", nullable = false)
	private Integer userId;

	@Column(name = "user_setting_id", columnDefinition = "int4", nullable = false)
	private Integer userSettingId;

	public UserSettingValuePK(Integer userSettingId, Integer userId) {
		super();
		this.userSettingId = userSettingId;
		this.userId = userId;
	}

	private UserSettingValuePK() {
		super();
	}

	public Integer getUserId() {
		return userId;
	}

	public Integer getUserSettingId() {
		return userSettingId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof UserSettingValuePK) {
			final UserSettingValuePK other = (UserSettingValuePK) obj;
			return new EqualsBuilder().append(userId, other.userId).append(userSettingId, other.userSettingId)
					.isEquals();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(userId).append(userSettingId).toHashCode();
	}
}
