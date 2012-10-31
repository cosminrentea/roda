package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-26T11:47:20.100+0300")
@StaticMetamodel(DocSrcType.class)
public class DocSrcType_ {
	public static volatile SingularAttribute<DocSrcType, Long> id_;
	public static volatile SingularAttribute<DocSrcType, DocDscrType> docdscrtype;
	public static volatile SingularAttribute<DocSrcType, TitlStmtType> titlStmt;
	public static volatile SingularAttribute<DocSrcType, RspStmtType> rspStmt;
	public static volatile SingularAttribute<DocSrcType, ProdStmtType> prodStmt;
	public static volatile SingularAttribute<DocSrcType, DistStmtType> distStmt;
	public static volatile SingularAttribute<DocSrcType, SerStmtType> serStmt;
	public static volatile ListAttribute<DocSrcType, VerStmtType> verStmt;
	public static volatile SingularAttribute<DocSrcType, BiblCitType> biblCit;
	public static volatile ListAttribute<DocSrcType, HoldingsType> holdings;
	public static volatile SingularAttribute<DocSrcType, String> id;
}
