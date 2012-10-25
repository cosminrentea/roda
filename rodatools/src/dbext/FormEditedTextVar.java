package dbext;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the form_edited_text_var database table.
 * 
 */
@Entity
@Table(name="form_edited_text_var")
public class FormEditedTextVar implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(name="instance_id")
	private Integer instanceId;

	@Column(nullable=false, length=255)
	private String text;

	//bi-directional many-to-one association to EditedVariable
    @ManyToOne
	@JoinColumn(name="variable_id", nullable=false)
	private EditedVariable editedVariable;

    public FormEditedTextVar() {
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

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public EditedVariable getEditedVariable() {
		return this.editedVariable;
	}

	public void setEditedVariable(EditedVariable editedVariable) {
		this.editedVariable = editedVariable;
	}
	
}