package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T03:11:55.477+0300")
@StaticMetamodel(ProdPlacType.class)
public class ProdPlacType_ {
	public static volatile SingularAttribute<ProdPlacType, Long> id_;
	public static volatile SingularAttribute<ProdPlacType, ProdStmtType> prodstmttype;
	public static volatile ListAttribute<ProdPlacType, String> content;
	public static volatile SingularAttribute<ProdPlacType, String> id;
	public static volatile SingularAttribute<ProdPlacType, String> xmlLang;
	public static volatile SingularAttribute<ProdPlacType, String> source;
}
