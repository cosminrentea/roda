package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-17T18:17:39.774+0300")
@StaticMetamodel(EditedVariable.class)
public class EditedVariable_ {
	public static volatile SingularAttribute<EditedVariable, Integer> variableId;
	public static volatile SingularAttribute<EditedVariable, Integer> instanceId;
	public static volatile SingularAttribute<EditedVariable, Integer> type;
	public static volatile SingularAttribute<EditedVariable, Variable> variable;
	public static volatile ListAttribute<EditedVariable, FormEditedNumberVar> formEditedNumberVars;
	public static volatile ListAttribute<EditedVariable, FormEditedTextVar> formEditedTextVars;
	public static volatile SingularAttribute<EditedVariable, OtherStatistic> otherStatistic;
}
