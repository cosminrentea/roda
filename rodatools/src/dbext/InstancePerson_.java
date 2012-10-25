package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T15:50:15.240+0300")
@StaticMetamodel(InstancePerson.class)
public class InstancePerson_ {
	public static volatile SingularAttribute<InstancePerson, InstancePersonPK> id;
	public static volatile SingularAttribute<InstancePerson, Instance> instance;
	public static volatile SingularAttribute<InstancePerson, InstancePersonAssoc> instancePersonAssoc;
	public static volatile SingularAttribute<InstancePerson, Person> person;
}
