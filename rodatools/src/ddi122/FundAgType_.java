package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T03:11:55.256+0300")
@StaticMetamodel(FundAgType.class)
public class FundAgType_ {
	public static volatile SingularAttribute<FundAgType, Long> id_;
	public static volatile SingularAttribute<FundAgType, ProdStmtType> prodstmttype;
	public static volatile ListAttribute<FundAgType, String> content;
	public static volatile SingularAttribute<FundAgType, String> id;
	public static volatile SingularAttribute<FundAgType, String> xmlLang;
	public static volatile SingularAttribute<FundAgType, String> source;
	public static volatile SingularAttribute<FundAgType, String> abbr;
	public static volatile SingularAttribute<FundAgType, String> role;
}
