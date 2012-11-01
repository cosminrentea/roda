package org.opendatafoundation.data.spss;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlTransient;

@Entity
public class SPSSDataRecordVariableValue {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@XmlTransient
	private long id_;

	public long getId_() {
		return id_;
	}

	public void setId_(long id_) {
		this.id_ = id_;
	}

	@OneToOne(cascade = CascadeType.ALL)
	SPSSVariable variable;

	@OneToOne(cascade = CascadeType.ALL)
	SPSSDataRecord dataRecord;

	String value;

	public SPSSDataRecordVariableValue() {
		super();
	}

	public SPSSDataRecordVariableValue(SPSSVariable variable,
			SPSSDataRecord dataRecord, String value) {
		super();
		this.variable = variable;
		this.dataRecord = dataRecord;
		this.value = value;
	}

}
