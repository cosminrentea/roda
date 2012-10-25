package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-17T18:17:41.064+0300")
@StaticMetamodel(Userdb.class)
public class Userdb_ {
	public static volatile SingularAttribute<Userdb, Integer> id;
	public static volatile SingularAttribute<Userdb, String> credentialProvider;
	public static volatile ListAttribute<Userdb, AuthData> authData;
	public static volatile ListAttribute<Userdb, UserAuthLog> userAuthLogs;
	public static volatile ListAttribute<Userdb, UserMessage> userMessages;
	public static volatile ListAttribute<Userdb, UserSettingValue> userSettingValues;
	public static volatile ListAttribute<Userdb, Roledb> roledb;
}
