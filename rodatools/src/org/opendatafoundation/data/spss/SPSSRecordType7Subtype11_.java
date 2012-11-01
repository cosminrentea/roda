package org.opendatafoundation.data.spss;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-31T14:18:53.969+0200")
@StaticMetamodel(SPSSRecordType7Subtype11.class)
public class SPSSRecordType7Subtype11_ extends SPSSAbstractRecordType_ {
	public static volatile SingularAttribute<SPSSRecordType7Subtype11, Integer> recordTypeCode;
	public static volatile SingularAttribute<SPSSRecordType7Subtype11, Integer> recordSubtypeCode;
	public static volatile SingularAttribute<SPSSRecordType7Subtype11, Integer> dataElementLength;
	public static volatile SingularAttribute<SPSSRecordType7Subtype11, Integer> numberOfDataElements;
	public static volatile ListAttribute<SPSSRecordType7Subtype11, VariableDisplayParams> variableDisplayParams;
}
