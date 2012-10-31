package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-26T11:28:32.336+0300")
@StaticMetamodel(AccsPlacType.class)
public class AccsPlacType_ {
	public static volatile SingularAttribute<AccsPlacType, Long> id_;
	public static volatile SingularAttribute<AccsPlacType, SetAvailType> setavailtype;
	public static volatile ListAttribute<AccsPlacType, String> content;
	public static volatile SingularAttribute<AccsPlacType, String> id;
	public static volatile SingularAttribute<AccsPlacType, String> uri;
}
