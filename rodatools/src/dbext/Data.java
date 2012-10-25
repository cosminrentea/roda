package dbext;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the "Data" database table.
 * 
 */
@Entity
@Table(name="\"Data\"")
public class Data implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DataPK id;

    public Data() {
    }

	public DataPK getId() {
		return this.id;
	}

	public void setId(DataPK id) {
		this.id = id;
	}
	
}