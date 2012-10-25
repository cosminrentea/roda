package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T15:50:17.137+0300")
@StaticMetamodel(Person.class)
public class Person_ {
	public static volatile SingularAttribute<Person, Integer> id;
	public static volatile SingularAttribute<Person, String> fname;
	public static volatile SingularAttribute<Person, String> lname;
	public static volatile SingularAttribute<Person, String> mname;
	public static volatile ListAttribute<Person, Email> emails;
	public static volatile ListAttribute<Person, InstancePerson> instancePersons;
	public static volatile ListAttribute<Person, Internet> internets;
	public static volatile SingularAttribute<Person, Prefix> prefix;
	public static volatile SingularAttribute<Person, Suffix> suffix;
	public static volatile ListAttribute<Person, PersonAddress> personAddresses;
	public static volatile ListAttribute<Person, PersonOrg> personOrgs;
	public static volatile ListAttribute<Person, StudyPerson> studyPersons;
}
