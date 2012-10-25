package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-17T18:17:41.107+0300")
@StaticMetamodel(UserSetting.class)
public class UserSetting_ {
	public static volatile SingularAttribute<UserSetting, Integer> id;
	public static volatile SingularAttribute<UserSetting, String> defaultValue;
	public static volatile SingularAttribute<UserSetting, String> description;
	public static volatile SingularAttribute<UserSetting, String> name;
	public static volatile SingularAttribute<UserSetting, String> predefinedValues;
	public static volatile SingularAttribute<UserSetting, UserSettingValue> userSettingValue;
	public static volatile SingularAttribute<UserSetting, UserSettingsGroup> userSettingsGroup;
}
