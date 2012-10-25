package dbext;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the user_auth_log database table.
 * 
 */
@Entity
@Table(name="user_auth_log")
public class UserAuthLog implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false, length=10)
	private String action;

	@Column(name="credential_identifier", nullable=false, length=250)
	private String credentialIdentifier;

	@Column(name="credential_provider", nullable=false, length=20)
	private String credentialProvider;

	@Column(name="error_message", nullable=false, length=250)
	private String errorMessage;

	@Column(nullable=false)
	private Timestamp timestamp;

	//bi-directional many-to-one association to User
    @ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user;

    public UserAuthLog() {
    }

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getCredentialIdentifier() {
		return this.credentialIdentifier;
	}

	public void setCredentialIdentifier(String credentialIdentifier) {
		this.credentialIdentifier = credentialIdentifier;
	}

	public String getCredentialProvider() {
		return this.credentialProvider;
	}

	public void setCredentialProvider(String credentialProvider) {
		this.credentialProvider = credentialProvider;
	}

	public String getErrorMessage() {
		return this.errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public Timestamp getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}