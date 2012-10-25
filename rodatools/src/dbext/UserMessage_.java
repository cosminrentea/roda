package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T15:50:17.539+0300")
@StaticMetamodel(UserMessage.class)
public class UserMessage_ {
	public static volatile SingularAttribute<UserMessage, Integer> id;
	public static volatile SingularAttribute<UserMessage, String> message;
	public static volatile SingularAttribute<UserMessage, User> user;
}
