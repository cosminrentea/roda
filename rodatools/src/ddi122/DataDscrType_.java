package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-26T11:43:08.530+0300")
@StaticMetamodel(DataDscrType.class)
public class DataDscrType_ {
	public static volatile SingularAttribute<DataDscrType, Long> id_;
	public static volatile SingularAttribute<DataDscrType, CodeBook> codebook;
	public static volatile ListAttribute<DataDscrType, VarType> var;
	public static volatile SingularAttribute<DataDscrType, String> id;
}
