package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T15:50:17.218+0300")
@StaticMetamodel(Prefix.class)
public class Prefix_ {
	public static volatile SingularAttribute<Prefix, Integer> id;
	public static volatile SingularAttribute<Prefix, String> name;
	public static volatile ListAttribute<Prefix, Person> persons;
}
