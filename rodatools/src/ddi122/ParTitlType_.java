package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-26T11:57:18.227+0300")
@StaticMetamodel(ParTitlType.class)
public class ParTitlType_ {
	public static volatile SingularAttribute<ParTitlType, Long> id_;
	public static volatile SingularAttribute<ParTitlType, TitlStmtType> titlstmttype;
	public static volatile ListAttribute<ParTitlType, String> content;
	public static volatile SingularAttribute<ParTitlType, String> id;
}
