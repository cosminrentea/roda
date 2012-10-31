package ddi122;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-26T12:40:15.693+0300")
@StaticMetamodel(VarType.class)
public class VarType_ {
	public static volatile SingularAttribute<VarType, Long> id_;
	public static volatile SingularAttribute<VarType, DataDscrType> datadscrtype;
	public static volatile ListAttribute<VarType, LocationType> location;
	public static volatile ListAttribute<VarType, LablType> labl;
	public static volatile ListAttribute<VarType, QstnType> qstn;
	public static volatile ListAttribute<VarType, ValrngType> valrng;
	public static volatile ListAttribute<VarType, InvalrngType> invalrng;
	public static volatile ListAttribute<VarType, UniverseType> universe;
	public static volatile ListAttribute<VarType, SumStatType> sumStat;
	public static volatile ListAttribute<VarType, TxtType> txt;
	public static volatile ListAttribute<VarType, CatgryType> catgry;
	public static volatile ListAttribute<VarType, VerStmtType> verStmt;
	public static volatile SingularAttribute<VarType, VarFormatType> varFormat;
	public static volatile SingularAttribute<VarType, String> id;
	public static volatile SingularAttribute<VarType, String> name;
	public static volatile SingularAttribute<VarType, String> wgt;
	public static volatile ListAttribute<VarType, String> wgtVar;
	public static volatile ListAttribute<VarType, String> weight;
	public static volatile ListAttribute<VarType, String> files;
	public static volatile SingularAttribute<VarType, String> dcml;
	public static volatile SingularAttribute<VarType, String> intrvl;
	public static volatile ListAttribute<VarType, String> sdatrefs;
	public static volatile ListAttribute<VarType, String> methrefs;
	public static volatile ListAttribute<VarType, String> pubrefs;
	public static volatile ListAttribute<VarType, String> access;
}
