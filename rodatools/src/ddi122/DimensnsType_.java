package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-26T11:44:40.997+0300")
@StaticMetamodel(DimensnsType.class)
public class DimensnsType_ {
	public static volatile SingularAttribute<DimensnsType, Long> id_;
	public static volatile ListAttribute<DimensnsType, CaseQntyType> caseQnty;
	public static volatile ListAttribute<DimensnsType, VarQntyType> varQnty;
	public static volatile SingularAttribute<DimensnsType, String> id;
}
