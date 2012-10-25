package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T15:50:17.329+0300")
@StaticMetamodel(Setting.class)
public class Setting_ {
	public static volatile SingularAttribute<Setting, Integer> id;
	public static volatile SingularAttribute<Setting, String> defaultValue;
	public static volatile SingularAttribute<Setting, String> description;
	public static volatile SingularAttribute<Setting, String> name;
	public static volatile SingularAttribute<Setting, String> predefinedValues;
	public static volatile SingularAttribute<Setting, SettingGroup> settingGroupBean;
	public static volatile SingularAttribute<Setting, SettingValue> settingValue;
}
