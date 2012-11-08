package org.opendatafoundation.data.spss;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-11-07T16:27:31.157+0200")
@StaticMetamodel(SPSSRecordType6.class)
public class SPSSRecordType6_ {
	public static volatile SingularAttribute<SPSSRecordType6, Integer> recordTypeCode;
	public static volatile SingularAttribute<SPSSRecordType6, Integer> numberOfLines;
	public static volatile ListAttribute<SPSSRecordType6, String> line;
	public static volatile SingularAttribute<SPSSRecordType6, Long> id_;
}
