package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T03:11:55.471+0300")
@StaticMetamodel(ProdDateType.class)
public class ProdDateType_ {
	public static volatile SingularAttribute<ProdDateType, Long> id_;
	public static volatile SingularAttribute<ProdDateType, ProdStmtType> prodstmttype;
	public static volatile ListAttribute<ProdDateType, String> content;
	public static volatile SingularAttribute<ProdDateType, String> id;
	public static volatile SingularAttribute<ProdDateType, String> xmlLang;
	public static volatile SingularAttribute<ProdDateType, String> source;
	public static volatile SingularAttribute<ProdDateType, String> date;
}
