package dbext;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T15:50:17.529+0300")
@StaticMetamodel(UserAuthLog.class)
public class UserAuthLog_ {
	public static volatile SingularAttribute<UserAuthLog, Integer> id;
	public static volatile SingularAttribute<UserAuthLog, String> action;
	public static volatile SingularAttribute<UserAuthLog, String> credentialIdentifier;
	public static volatile SingularAttribute<UserAuthLog, String> credentialProvider;
	public static volatile SingularAttribute<UserAuthLog, String> errorMessage;
	public static volatile SingularAttribute<UserAuthLog, Timestamp> timestamp;
	public static volatile SingularAttribute<UserAuthLog, User> user;
}
