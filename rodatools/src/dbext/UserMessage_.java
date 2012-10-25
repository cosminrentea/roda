package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-17T18:17:41.085+0300")
@StaticMetamodel(UserMessage.class)
public class UserMessage_ {
	public static volatile SingularAttribute<UserMessage, Integer> id;
	public static volatile SingularAttribute<UserMessage, String> message;
	public static volatile SingularAttribute<UserMessage, Userdb> userdb;
}
