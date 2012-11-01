package org.opendatafoundation.data.spss;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-11-01T13:12:02.400+0200")
@StaticMetamodel(SPSSDataRecordVariableValue.class)
public class SPSSDataRecordVariableValue_ {
	public static volatile SingularAttribute<SPSSDataRecordVariableValue, Long> id_;
	public static volatile SingularAttribute<SPSSDataRecordVariableValue, SPSSVariable> variable;
	public static volatile SingularAttribute<SPSSDataRecordVariableValue, SPSSDataRecord> dataRecord;
	public static volatile SingularAttribute<SPSSDataRecordVariableValue, String> value;
}
