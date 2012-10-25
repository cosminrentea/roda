package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-17T18:17:40.657+0300")
@StaticMetamodel(Roledb.class)
public class Roledb_ {
	public static volatile SingularAttribute<Roledb, Integer> id;
	public static volatile SingularAttribute<Roledb, String> description;
	public static volatile SingularAttribute<Roledb, String> name;
	public static volatile ListAttribute<Roledb, Userdb> userdb;
}
