package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T15:50:17.516+0300")
@StaticMetamodel(User.class)
public class User_ {
	public static volatile SingularAttribute<User, Integer> id;
	public static volatile SingularAttribute<User, String> credentialProvider;
	public static volatile ListAttribute<User, AuthData> authData;
	public static volatile ListAttribute<User, UserAuthLog> userAuthLogs;
	public static volatile ListAttribute<User, UserMessage> userMessages;
	public static volatile ListAttribute<User, Role> roles;
	public static volatile ListAttribute<User, UserSettingValue> userSettingValues;
}
