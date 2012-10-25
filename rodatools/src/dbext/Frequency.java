package dbext;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the frequency database table.
 * 
 */
@Entity
@Table(name="frequency")
public class Frequency implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false)
	private double value;

    public Frequency() {
    }

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getValue() {
		return this.value;
	}

	public void setValue(double value) {
		this.value = value;
	}

}