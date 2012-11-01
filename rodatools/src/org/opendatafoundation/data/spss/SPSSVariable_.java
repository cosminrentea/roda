package org.opendatafoundation.data.spss;

import javax.annotation.Generated;
import javax.persistence.metamodel.MapAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import org.opendatafoundation.data.spss.SPSSVariable.VariableType;

@Generated(value="Dali", date="2012-10-31T15:06:17.134+0200")
@StaticMetamodel(SPSSVariable.class)
public class SPSSVariable_ {
	public static volatile SingularAttribute<SPSSVariable, Long> id_;
	public static volatile SingularAttribute<SPSSVariable, SPSSRecordType2> variableRecord;
	public static volatile SingularAttribute<SPSSVariable, SPSSRecordType3> valueLabelRecord;
	public static volatile SingularAttribute<SPSSVariable, VariableType> type;
	public static volatile SingularAttribute<SPSSVariable, Integer> variableNumber;
	public static volatile SingularAttribute<SPSSVariable, String> variableName;
	public static volatile SingularAttribute<SPSSVariable, String> variableShortName;
	public static volatile SingularAttribute<SPSSVariable, Integer> measure;
	public static volatile SingularAttribute<SPSSVariable, Integer> displayWidth;
	public static volatile SingularAttribute<SPSSVariable, Integer> alignment;
	public static volatile MapAttribute<SPSSVariable, String, SPSSVariableCategory> categoryMap;
}
