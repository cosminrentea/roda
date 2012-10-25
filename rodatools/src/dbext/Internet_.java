package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-17T18:17:40.195+0300")
@StaticMetamodel(Internet.class)
public class Internet_ {
	public static volatile SingularAttribute<Internet, Integer> id;
	public static volatile SingularAttribute<Internet, String> content;
	public static volatile SingularAttribute<Internet, Integer> entityType;
	public static volatile SingularAttribute<Internet, Integer> internetTypeId;
	public static volatile SingularAttribute<Internet, Org> org;
	public static volatile SingularAttribute<Internet, Person> person;
}
