package ro.roda.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.jpa.identifier.RooIdentifier;

@Embeddable
@Configurable

public final class UserSettingValuePK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "user_setting_id", columnDefinition = "int4", nullable = false)
    private Integer userSettingId;

	@Column(name = "user_id", columnDefinition = "int4", nullable = false)
    private Integer userId;

	public UserSettingValuePK(Integer userSettingId, Integer userId) {
        super();
        this.userSettingId = userSettingId;
        this.userId = userId;
    }

	private UserSettingValuePK() {
        super();
    }

	public Integer getUserSettingId() {
        return userSettingId;
    }

	public Integer getUserId() {
        return userId;
    }

	public String toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }

	public static UserSettingValuePK fromJsonToUserSettingValuePK(String json) {
        return new JSONDeserializer<UserSettingValuePK>().use(null, UserSettingValuePK.class).deserialize(json);
    }

	public static String toJsonArray(Collection<UserSettingValuePK> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }

	public static Collection<UserSettingValuePK> fromJsonArrayToUserSettingValuePKs(String json) {
        return new JSONDeserializer<List<UserSettingValuePK>>().use(null, ArrayList.class).use("values", UserSettingValuePK.class).deserialize(json);
    }
}
