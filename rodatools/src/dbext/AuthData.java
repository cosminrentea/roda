package dbext;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the auth_data database table.
 * 
 */
@Entity
@Table(name="auth_data")
public class AuthData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(name="credential_provider", nullable=false, length=20)
	private String credentialProvider;

	@Column(name="field_name", nullable=false, length=100)
	private String fieldName;

	@Column(name="field_value", nullable=false, length=250)
	private String fieldValue;

	//bi-directional many-to-one association to User
    @ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user;

    public AuthData() {
    }

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCredentialProvider() {
		return this.credentialProvider;
	}

	public void setCredentialProvider(String credentialProvider) {
		this.credentialProvider = credentialProvider;
	}

	public String getFieldName() {
		return this.fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldValue() {
		return this.fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}