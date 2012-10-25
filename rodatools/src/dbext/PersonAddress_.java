package dbext;

import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T15:50:17.152+0300")
@StaticMetamodel(PersonAddress.class)
public class PersonAddress_ {
	public static volatile SingularAttribute<PersonAddress, PersonAddressPK> id;
	public static volatile SingularAttribute<PersonAddress, Timestamp> dateend;
	public static volatile SingularAttribute<PersonAddress, Timestamp> datestart;
	public static volatile SingularAttribute<PersonAddress, Address> address;
	public static volatile SingularAttribute<PersonAddress, Person> person;
}
