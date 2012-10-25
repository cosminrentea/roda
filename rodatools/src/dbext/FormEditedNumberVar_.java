package dbext;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-17T18:17:39.834+0300")
@StaticMetamodel(FormEditedNumberVar.class)
public class FormEditedNumberVar_ {
	public static volatile SingularAttribute<FormEditedNumberVar, Integer> id;
	public static volatile SingularAttribute<FormEditedNumberVar, Integer> instanceId;
	public static volatile SingularAttribute<FormEditedNumberVar, BigDecimal> value;
	public static volatile SingularAttribute<FormEditedNumberVar, EditedVariable> editedVariable;
}
