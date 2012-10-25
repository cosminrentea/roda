package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-17T18:17:40.734+0300")
@StaticMetamodel(SelectionVariableItem.class)
public class SelectionVariableItem_ {
	public static volatile SingularAttribute<SelectionVariableItem, SelectionVariableItemPK> id;
	public static volatile SingularAttribute<SelectionVariableItem, Integer> instanceId;
	public static volatile SingularAttribute<SelectionVariableItem, Integer> ordering;
	public static volatile ListAttribute<SelectionVariableItem, FormSelectionVar> formSelectionVars;
	public static volatile SingularAttribute<SelectionVariableItem, Frequency> frequency;
	public static volatile ListAttribute<SelectionVariableItem, InstanceDocument> instanceDocuments;
	public static volatile SingularAttribute<SelectionVariableItem, Item> item;
	public static volatile SingularAttribute<SelectionVariableItem, SelectionVariable> selectionVariable;
}
