package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-17T18:17:41.020+0300")
@StaticMetamodel(UnitAnalysi.class)
public class UnitAnalysi_ {
	public static volatile SingularAttribute<UnitAnalysi, Integer> id;
	public static volatile SingularAttribute<UnitAnalysi, String> description;
	public static volatile SingularAttribute<UnitAnalysi, String> name;
	public static volatile ListAttribute<UnitAnalysi, Instance> instances;
}
