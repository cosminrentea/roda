package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-17T18:17:39.994+0300")
@StaticMetamodel(InstanceAcl.class)
public class InstanceAcl_ {
	public static volatile SingularAttribute<InstanceAcl, Integer> aroId;
	public static volatile SingularAttribute<InstanceAcl, Integer> aroType;
	public static volatile SingularAttribute<InstanceAcl, Boolean> delete;
	public static volatile SingularAttribute<InstanceAcl, Boolean> modacl;
	public static volatile SingularAttribute<InstanceAcl, Boolean> read;
	public static volatile SingularAttribute<InstanceAcl, Boolean> update;
	public static volatile SingularAttribute<InstanceAcl, Instance> instance;
}
