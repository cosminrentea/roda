package org.opendatafoundation.data.spss;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-11-05T00:33:24.169+0200")
@StaticMetamodel(SPSSRecordType6.class)
public class SPSSRecordType6_ extends SPSSAbstractRecordType_ {
	public static volatile SingularAttribute<SPSSRecordType6, Integer> recordTypeCode;
	public static volatile SingularAttribute<SPSSRecordType6, Integer> numberOfLines;
	public static volatile ListAttribute<SPSSRecordType6, String> line;
}
