package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-17T18:17:41.160+0300")
@StaticMetamodel(UserSettingValue.class)
public class UserSettingValue_ {
	public static volatile SingularAttribute<UserSettingValue, UserSettingValuePK> id;
	public static volatile SingularAttribute<UserSettingValue, String> value;
	public static volatile SingularAttribute<UserSettingValue, UserSetting> userSetting;
	public static volatile SingularAttribute<UserSettingValue, Userdb> userdb;
}
