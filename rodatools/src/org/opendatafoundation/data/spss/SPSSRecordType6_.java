package org.opendatafoundation.data.spss;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-11-08T15:51:29.792+0200")
@StaticMetamodel(SPSSRecordType6.class)
public class SPSSRecordType6_ {
	public static volatile SingularAttribute<SPSSRecordType6, Long> id_;
	public static volatile SingularAttribute<SPSSRecordType6, Integer> recordTypeCode;
	public static volatile SingularAttribute<SPSSRecordType6, Integer> numberOfLines;
	public static volatile ListAttribute<SPSSRecordType6, String> line;
}
