package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-26T11:57:58.746+0300")
@StaticMetamodel(ProdStmtType.class)
public class ProdStmtType_ {
	public static volatile SingularAttribute<ProdStmtType, Long> id_;
	public static volatile ListAttribute<ProdStmtType, ProducerType> producer;
	public static volatile SingularAttribute<ProdStmtType, CopyrightType> copyright;
	public static volatile ListAttribute<ProdStmtType, ProdDateType> prodDate;
	public static volatile ListAttribute<ProdStmtType, ProdPlacType> prodPlac;
	public static volatile ListAttribute<ProdStmtType, SoftwareType> software;
	public static volatile ListAttribute<ProdStmtType, FundAgType> fundAg;
	public static volatile ListAttribute<ProdStmtType, GrantNoType> grantNo;
	public static volatile SingularAttribute<ProdStmtType, String> id;
}
