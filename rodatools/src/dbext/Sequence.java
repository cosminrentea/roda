package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the sequence database table.
 * 
 */
@Entity
@Table(name="sequence")
public class Sequence implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="seq_name", unique=true, nullable=false, length=50)
	private String seqName;

	@Column(name="seq_count", precision=38)
	private BigDecimal seqCount;

    public Sequence() {
    }

	public String getSeqName() {
		return this.seqName;
	}

	public void setSeqName(String seqName) {
		this.seqName = seqName;
	}

	public BigDecimal getSeqCount() {
		return this.seqCount;
	}

	public void setSeqCount(BigDecimal seqCount) {
		this.seqCount = seqCount;
	}

}