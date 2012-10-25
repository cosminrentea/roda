package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-17T18:17:39.235+0300")
@StaticMetamodel(AuthData.class)
public class AuthData_ {
	public static volatile SingularAttribute<AuthData, Integer> id;
	public static volatile SingularAttribute<AuthData, String> credentialProvider;
	public static volatile SingularAttribute<AuthData, String> fieldName;
	public static volatile SingularAttribute<AuthData, String> fieldValue;
	public static volatile SingularAttribute<AuthData, Userdb> userdb;
}
