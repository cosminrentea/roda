package ro.roda.ddi;

//import elsst.ElsstTerm;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "Dali", date = "2012-11-08T15:51:29.393+0200")
@StaticMetamodel(VarType.class)
public class VarType_ {
	public static volatile SingularAttribute<VarType, Long> id_;
	public static volatile SingularAttribute<VarType, DataDscrType> datadscrtype;
	// public static volatile ListAttribute<VarType, ElsstTerm> elsstTerm;
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
	public static volatile SingularAttribute<VarType, String> files;
	public static volatile SingularAttribute<VarType, String> intrvl;
}
