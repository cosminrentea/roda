package dbext;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the user_messages database table.
 * 
 */
@Entity
@Table(name="user_messages")
public class UserMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private Integer id;

	@Column(nullable=false, length=255)
	private String message;

	//bi-directional many-to-one association to User
    @ManyToOne
	@JoinColumn(name="user_id", nullable=false)
	private User user;

    public UserMessage() {
    }

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}