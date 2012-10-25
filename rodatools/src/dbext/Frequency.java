package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the frequency database table.
 * 
 */
@Entity
@Table(name = "frequency")
public class Frequency implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(nullable = false)
	private float value;

	// bi-directional many-to-one association to SelectionVariableItem
	@OneToMany(mappedBy = "frequency")
	private List<SelectionVariableItem> selectionVariableItems;

	public Frequency() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public float getValue() {
		return this.value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public List<SelectionVariableItem> getSelectionVariableItems() {
		return this.selectionVariableItems;
	}

	public void setSelectionVariableItems(
			List<SelectionVariableItem> selectionVariableItems) {
		this.selectionVariableItems = selectionVariableItems;
	}

}