package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T03:11:54.895+0300")
@StaticMetamodel(AltTitlType.class)
public class AltTitlType_ {
	public static volatile SingularAttribute<AltTitlType, Long> id_;
	public static volatile SingularAttribute<AltTitlType, TitlStmtType> titlstmttype;
	public static volatile ListAttribute<AltTitlType, String> content;
	public static volatile SingularAttribute<AltTitlType, String> id;
	public static volatile SingularAttribute<AltTitlType, String> xmlLang;
	public static volatile SingularAttribute<AltTitlType, String> source;
}
