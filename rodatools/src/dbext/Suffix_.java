package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T15:50:17.496+0300")
@StaticMetamodel(Suffix.class)
public class Suffix_ {
	public static volatile SingularAttribute<Suffix, Integer> id;
	public static volatile SingularAttribute<Suffix, String> name;
	public static volatile ListAttribute<Suffix, Person> persons;
}
