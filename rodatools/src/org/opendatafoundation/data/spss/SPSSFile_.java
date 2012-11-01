package org.opendatafoundation.data.spss;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-11-01T15:27:13.479+0200")
@StaticMetamodel(SPSSFile.class)
public class SPSSFile_ {
	public static volatile SingularAttribute<SPSSFile, Long> id_;
	public static volatile SingularAttribute<SPSSFile, String> uniqueID;
	public static volatile SingularAttribute<SPSSFile, Boolean> isBigEndian;
	public static volatile SingularAttribute<SPSSFile, SPSSRecordType1> infoRecord;
	public static volatile MapAttribute<SPSSFile, Integer, SPSSVariable> variableMap;
	public static volatile SingularAttribute<SPSSFile, SPSSRecordType6> documentationRecord;
	public static volatile SingularAttribute<SPSSFile, SPSSRecordType7Subtype3> integerInformationRecord;
	public static volatile SingularAttribute<SPSSFile, SPSSRecordType7Subtype4> floatInformationRecord;
	public static volatile SingularAttribute<SPSSFile, SPSSRecordType7Subtype5> variableSetsInformationRecord;
	public static volatile SingularAttribute<SPSSFile, SPSSRecordType7Subtype11> variableDisplayParamsRecord;
	public static volatile SingularAttribute<SPSSFile, SPSSRecordType7Subtype13> longVariableNamesRecord;
	public static volatile ListAttribute<SPSSFile, SPSSDataRecordVariableValue> values;
}
