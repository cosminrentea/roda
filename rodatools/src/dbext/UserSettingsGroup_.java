package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T15:50:17.558+0300")
@StaticMetamodel(UserSettingsGroup.class)
public class UserSettingsGroup_ {
	public static volatile SingularAttribute<UserSettingsGroup, Integer> id;
	public static volatile SingularAttribute<UserSettingsGroup, String> description;
	public static volatile SingularAttribute<UserSettingsGroup, String> name;
	public static volatile ListAttribute<UserSettingsGroup, UserSetting> userSettings;
}
