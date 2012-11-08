package org.opendatafoundation.data.spss;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-11-07T16:23:49.382+0200")
@StaticMetamodel(SPSSRecordType1.class)
public class SPSSRecordType1_ {
	public static volatile SingularAttribute<SPSSRecordType1, String> recordTypeCode;
	public static volatile SingularAttribute<SPSSRecordType1, String> productIdentification;
	public static volatile SingularAttribute<SPSSRecordType1, Integer> layoutCode;
	public static volatile SingularAttribute<SPSSRecordType1, Integer> OBSperObservation;
	public static volatile SingularAttribute<SPSSRecordType1, Integer> compressionSwitch;
	public static volatile SingularAttribute<SPSSRecordType1, Integer> weightVariableIndex;
	public static volatile SingularAttribute<SPSSRecordType1, Integer> numberOfCases;
	public static volatile SingularAttribute<SPSSRecordType1, Double> compressionBias;
	public static volatile SingularAttribute<SPSSRecordType1, String> creationDate;
	public static volatile SingularAttribute<SPSSRecordType1, String> creationTime;
	public static volatile SingularAttribute<SPSSRecordType1, String> fileLabel;
	public static volatile SingularAttribute<SPSSRecordType1, Long> id_;
}
