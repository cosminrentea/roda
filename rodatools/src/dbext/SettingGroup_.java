package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-17T18:17:40.776+0300")
@StaticMetamodel(SettingGroup.class)
public class SettingGroup_ {
	public static volatile SingularAttribute<SettingGroup, Integer> id;
	public static volatile SingularAttribute<SettingGroup, String> description;
	public static volatile SingularAttribute<SettingGroup, String> name;
	public static volatile SingularAttribute<SettingGroup, Integer> parent;
	public static volatile ListAttribute<SettingGroup, Setting> settings;
}
