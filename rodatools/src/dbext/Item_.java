package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-17T18:17:40.206+0300")
@StaticMetamodel(Item.class)
public class Item_ {
	public static volatile SingularAttribute<Item, Integer> id;
	public static volatile SingularAttribute<Item, String> name;
	public static volatile ListAttribute<Item, SelectionVariableItem> selectionVariableItems;
}
