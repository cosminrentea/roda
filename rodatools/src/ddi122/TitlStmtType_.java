package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-17T18:17:43.270+0300")
@StaticMetamodel(TitlStmtType.class)
public class TitlStmtType_ {
	public static volatile SingularAttribute<TitlStmtType, Long> id_;
	public static volatile SingularAttribute<TitlStmtType, TitlType> titl;
	public static volatile ListAttribute<TitlStmtType, AltTitlType> altTitl;
	public static volatile ListAttribute<TitlStmtType, ParTitlType> parTitl;
	public static volatile ListAttribute<TitlStmtType, IDNoType> idNo;
	public static volatile SingularAttribute<TitlStmtType, String> id;
	public static volatile SingularAttribute<TitlStmtType, String> xmlLang;
	public static volatile SingularAttribute<TitlStmtType, String> source;
}
