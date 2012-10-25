package dbext;

import java.math.BigDecimal;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2012-10-25T15:50:17.319+0300")
@StaticMetamodel(Sequence.class)
public class Sequence_ {
	public static volatile SingularAttribute<Sequence, String> seqName;
	public static volatile SingularAttribute<Sequence, BigDecimal> seqCount;
}
