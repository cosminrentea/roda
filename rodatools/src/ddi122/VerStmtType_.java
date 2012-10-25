package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T03:11:55.837+0300")
@StaticMetamodel(VerStmtType.class)
public class VerStmtType_ {
	public static volatile SingularAttribute<VerStmtType, Long> id_;
	public static volatile SingularAttribute<VerStmtType, VarType> vartype;
	public static volatile SingularAttribute<VerStmtType, CitationType> citationtype;
	public static volatile SingularAttribute<VerStmtType, DocSrcType> docsrctype;
	public static volatile SingularAttribute<VerStmtType, VersionType> version;
	public static volatile SingularAttribute<VerStmtType, VerRespType> verResp;
	public static volatile SingularAttribute<VerStmtType, String> id;
	public static volatile SingularAttribute<VerStmtType, String> xmlLang;
	public static volatile SingularAttribute<VerStmtType, String> source;
}
