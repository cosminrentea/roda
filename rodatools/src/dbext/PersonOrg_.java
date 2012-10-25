package dbext;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T15:50:17.185+0300")
@StaticMetamodel(PersonOrg.class)
public class PersonOrg_ {
	public static volatile SingularAttribute<PersonOrg, PersonOrgPK> id;
	public static volatile SingularAttribute<PersonOrg, Timestamp> dateend;
	public static volatile SingularAttribute<PersonOrg, Timestamp> datestart;
	public static volatile SingularAttribute<PersonOrg, Org> org;
	public static volatile SingularAttribute<PersonOrg, Person> person;
	public static volatile SingularAttribute<PersonOrg, PersonRole> personRole;
}
