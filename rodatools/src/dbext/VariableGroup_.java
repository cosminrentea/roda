package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-17T18:17:41.291+0300")
@StaticMetamodel(VariableGroup.class)
public class VariableGroup_ {
	public static volatile SingularAttribute<VariableGroup, Integer> id;
	public static volatile SingularAttribute<VariableGroup, String> name;
	public static volatile ListAttribute<VariableGroup, InstanceVarGroup> instanceVarGroups;
}
