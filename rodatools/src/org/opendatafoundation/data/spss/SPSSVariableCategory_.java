package org.opendatafoundation.data.spss;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-11-01T15:27:13.534+0200")
@StaticMetamodel(SPSSVariableCategory.class)
public class SPSSVariableCategory_ {
	public static volatile SingularAttribute<SPSSVariableCategory, Long> id_;
	public static volatile SingularAttribute<SPSSVariableCategory, Double> value;
	public static volatile SingularAttribute<SPSSVariableCategory, String> strValue;
	public static volatile SingularAttribute<SPSSVariableCategory, String> label;
	public static volatile SingularAttribute<SPSSVariableCategory, Boolean> isMissing;
	public static volatile SingularAttribute<SPSSVariableCategory, Long> freq;
	public static volatile SingularAttribute<SPSSVariableCategory, Long> wgtFreq;
}
