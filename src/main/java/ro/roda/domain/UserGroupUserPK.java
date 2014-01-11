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

@Configurable
@Embeddable
public final class UserGroupUserPK implements Serializable {

	private static final long serialVersionUID = 1L;

	public static Collection<UserGroupUserPK> fromJsonArrayToUserGroupUserPKs(String json) {
		return new JSONDeserializer<List<UserGroupUserPK>>().use(null, ArrayList.class)
				.use("values", UserGroupUserPK.class).deserialize(json);
	}

	public static UserGroupUserPK fromJsonToUserGroupUserPK(String json) {
		return new JSONDeserializer<UserGroupUserPK>().use(null, UserGroupUserPK.class).deserialize(json);
	}

	public static String toJsonArray(Collection<UserGroupUserPK> collection) {
		return new JSONSerializer().exclude("*.class").serialize(collection);
	}

	@Column(name = "user_id", columnDefinition = "int4", nullable = false)
	private Integer userId;

	@Column(name = "user_group_id", columnDefinition = "int4", nullable = false)
	private Integer userGroupId;

	public UserGroupUserPK(Integer userId, Integer userGroupId) {
		super();
		this.userId = userId;
		this.userGroupId = userGroupId;
	}

	private UserGroupUserPK() {
		super();
	}

	public Integer getUserId() {
		return userId;
	}

	public Integer getUserGroupId() {
		return userGroupId;
	}

	public String toJson() {
		return new JSONSerializer().exclude("*.class").serialize(this);
	}

	@Override
	public boolean equals(final Object obj) {
		if (obj instanceof UserGroupUserPK) {
			final UserGroupUserPK other = (UserGroupUserPK) obj;
			return new EqualsBuilder().append(userId, other.userId).append(userGroupId, other.userGroupId).isEquals();
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(userId).append(userGroupId).toHashCode();
	}
}
