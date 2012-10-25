package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-17T18:17:40.707+0300")
@StaticMetamodel(SelectionVariable.class)
public class SelectionVariable_ {
	public static volatile SingularAttribute<SelectionVariable, Integer> id;
	public static volatile SingularAttribute<SelectionVariable, Integer> instanceId;
	public static volatile SingularAttribute<SelectionVariable, Integer> maxCount;
	public static volatile SingularAttribute<SelectionVariable, Integer> minCount;
	public static volatile SingularAttribute<SelectionVariable, Variable> variable;
	public static volatile ListAttribute<SelectionVariable, SelectionVariableItem> selectionVariableItems;
}
