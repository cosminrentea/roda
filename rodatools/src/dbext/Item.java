package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

/**
 * The persistent class for the item database table.
 * 
 */
@Entity
@Table(name = "item")
public class Item implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(nullable = false, length = 50)
	private String name;

	// bi-directional many-to-one association to SelectionVariableItem
	@OneToMany(mappedBy = "item")
	private List<SelectionVariableItem> selectionVariableItems;

	public Item() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SelectionVariableItem> getSelectionVariableItems() {
		return this.selectionVariableItems;
	}

	public void setSelectionVariableItems(
			List<SelectionVariableItem> selectionVariableItems) {
		this.selectionVariableItems = selectionVariableItems;
	}

}