package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T15:50:17.233+0300")
@StaticMetamodel(Role.class)
public class Role_ {
	public static volatile SingularAttribute<Role, Integer> id;
	public static volatile SingularAttribute<Role, String> description;
	public static volatile SingularAttribute<Role, String> name;
	public static volatile ListAttribute<Role, User> users;
}
