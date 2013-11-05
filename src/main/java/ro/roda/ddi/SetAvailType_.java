package ro.roda.ddi;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-26T12:01:19.970+0300")
@StaticMetamodel(SetAvailType.class)
public class SetAvailType_ {
	public static volatile SingularAttribute<SetAvailType, Long> id_;
	public static volatile SingularAttribute<SetAvailType, DataAccsType> dataaccess;
	public static volatile ListAttribute<SetAvailType, AccsPlacType> accsPlac;
	public static volatile SingularAttribute<SetAvailType, OrigArchType> origArch;
	public static volatile SingularAttribute<SetAvailType, String> id;
	public static volatile SingularAttribute<SetAvailType, String> media;
	public static volatile SingularAttribute<SetAvailType, String> callno;
	public static volatile SingularAttribute<SetAvailType, String> label;
	public static volatile SingularAttribute<SetAvailType, String> type;
}
