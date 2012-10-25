package dbext;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-17T18:17:41.235+0300")
@StaticMetamodel(Variable.class)
public class Variable_ {
	public static volatile SingularAttribute<Variable, Integer> id;
	public static volatile SingularAttribute<Variable, String> label;
	public static volatile SingularAttribute<Variable, String> operatorInstructions;
	public static volatile SingularAttribute<Variable, Integer> ordering;
	public static volatile SingularAttribute<Variable, Integer> responseType;
	public static volatile SingularAttribute<Variable, Integer> type;
	public static volatile ListAttribute<Variable, Concept> concepts;
	public static volatile SingularAttribute<Variable, EditedVariable> editedVariable;
	public static volatile SingularAttribute<Variable, InstanceVarGroup> instanceVarGroup;
	public static volatile SingularAttribute<Variable, SelectionVariable> selectionVariable;
	public static volatile ListAttribute<Variable, Skip> skips1;
	public static volatile ListAttribute<Variable, Skip> skips2;
	public static volatile SingularAttribute<Variable, Instance> instance;
}
