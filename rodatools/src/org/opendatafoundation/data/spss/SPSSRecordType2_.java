package org.opendatafoundation.data.spss;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-11-08T15:51:29.687+0200")
@StaticMetamodel(SPSSRecordType2.class)
public class SPSSRecordType2_ {
	public static volatile SingularAttribute<SPSSRecordType2, Long> id_;
	public static volatile SingularAttribute<SPSSRecordType2, Integer> recordTypeCode;
	public static volatile SingularAttribute<SPSSRecordType2, Integer> variableTypeCode;
	public static volatile SingularAttribute<SPSSRecordType2, Integer> hasLabel;
	public static volatile SingularAttribute<SPSSRecordType2, Integer> missingValueFormatCode;
	public static volatile SingularAttribute<SPSSRecordType2, Integer> printFormatCode;
	public static volatile SingularAttribute<SPSSRecordType2, Integer> printFormatDecimals;
	public static volatile SingularAttribute<SPSSRecordType2, Integer> printFormatWidth;
	public static volatile SingularAttribute<SPSSRecordType2, Integer> printFormatType;
	public static volatile SingularAttribute<SPSSRecordType2, Integer> printFormatZero;
	public static volatile SingularAttribute<SPSSRecordType2, Integer> writeFormatCode;
	public static volatile SingularAttribute<SPSSRecordType2, Integer> writeFormatDecimals;
	public static volatile SingularAttribute<SPSSRecordType2, Integer> writeFormatWidth;
	public static volatile SingularAttribute<SPSSRecordType2, Integer> writeFormatType;
	public static volatile SingularAttribute<SPSSRecordType2, Integer> writeFormatZero;
	public static volatile SingularAttribute<SPSSRecordType2, String> name;
	public static volatile SingularAttribute<SPSSRecordType2, Integer> labelLength;
	public static volatile SingularAttribute<SPSSRecordType2, String> label;
	public static volatile SingularAttribute<SPSSRecordType2, Byte> missingValue;
	public static volatile SingularAttribute<SPSSRecordType2, SPSSRecordType3> valueLabelSet;
}
