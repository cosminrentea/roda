package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-17T18:17:39.934+0300")
@StaticMetamodel(Frequency.class)
public class Frequency_ {
	public static volatile SingularAttribute<Frequency, Integer> id;
	public static volatile SingularAttribute<Frequency, Float> value;
	public static volatile ListAttribute<Frequency, SelectionVariableItem> selectionVariableItems;
}
