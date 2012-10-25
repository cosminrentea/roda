package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T15:50:17.203+0300")
@StaticMetamodel(PersonRole.class)
public class PersonRole_ {
	public static volatile SingularAttribute<PersonRole, Integer> id;
	public static volatile SingularAttribute<PersonRole, String> name;
	public static volatile ListAttribute<PersonRole, PersonOrg> personOrgs;
}
