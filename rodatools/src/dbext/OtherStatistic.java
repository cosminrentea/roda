package dbext;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the other_statistics database table.
 * 
 */
@Entity
@Table(name="other_statistics")
public class OtherStatistic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(name="instance_id")
	private Integer instanceId;

	@Column(name="statistic_name", nullable=false, length=30)
	private String statisticName;

	@Column(name="statistic_value", nullable=false)
	private double statisticValue;

	//bi-directional many-to-one association to EditedVariable
    @ManyToOne
	@JoinColumn(name="variable_id", nullable=false)
	private EditedVariable editedVariable;

    public OtherStatistic() {
    }

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getInstanceId() {
		return this.instanceId;
	}

	public void setInstanceId(Integer instanceId) {
		this.instanceId = instanceId;
	}

	public String getStatisticName() {
		return this.statisticName;
	}

	public void setStatisticName(String statisticName) {
		this.statisticName = statisticName;
	}

	public double getStatisticValue() {
		return this.statisticValue;
	}

	public void setStatisticValue(double statisticValue) {
		this.statisticValue = statisticValue;
	}

	public EditedVariable getEditedVariable() {
		return this.editedVariable;
	}

	public void setEditedVariable(EditedVariable editedVariable) {
		this.editedVariable = editedVariable;
	}
	
}