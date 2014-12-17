package ro.roda.domainjson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.UserMessage;
import ro.roda.transformer.FieldNameTransformer;
import flexjson.JSONSerializer;

@Configurable
public class UserMessages extends JsonInfo {

	public static String toJsonArray(Collection<UserMessages> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "id", "name", "type");
		serializer.include("timestamp", "fromid", "fromusername", "subject", "message");

		serializer.transform(DATE_TRANSFORMER, Date.class);
		serializer.transform(new FieldNameTransformer("datetime"), "timestamp");

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<UserMessages> findUserMessages(Integer id) {
		List<UserMessages> userMessages = new ArrayList<UserMessages>();

		List<UserMessage> incomingMessages = findUserIncomingMessages(id);

		if (incomingMessages != null && incomingMessages.size() > 0) {
			Iterator<UserMessage> incomingMessagesIterator = incomingMessages.iterator();
			while (incomingMessagesIterator.hasNext()) {
				UserMessage message = incomingMessagesIterator.next();
				userMessages.add(new UserMessages(message.getTimestamp(), message.getSubject(), message.isRead(),
						message.getMessage(), message.getFromUserId().getId(), message.getFromUserId().getUsername()));
			}
		}

		// List<UserMessage> outcomingMessages = findUserOutcomingMessages(id);
		//
		// if (outcomingMessages != null && outcomingMessages.size() > 0) {
		// Iterator<UserMessage> outcomingMessagesIterator =
		// outcomingMessages.iterator();
		// while (outcomingMessagesIterator.hasNext()) {
		// UserMessage message = outcomingMessagesIterator.next();
		// userMessages.add(new UserMessages(null, null, "outcoming", false,
		// message.getMessage()));
		// }
		// }

		return userMessages;
	}

	public static List<UserMessage> findUserIncomingMessages(int id) {
		return UserMessage.entityManager()
				.createQuery("SELECT o FROM UserMessage o WHERE o.toUserId = " + id, UserMessage.class).getResultList();
	}

	public static List<UserMessage> findUserOutcomingMessages(int id) {
		return UserMessage.entityManager()
				.createQuery("SELECT o FROM UserMessage o WHERE o.fromUserId = " + id, UserMessage.class)
				.getResultList();
	}

	private Date timestamp;

	private String subject;

	// private String direction;

	private boolean read;

	private String message;

	private Integer fromid;

	private String fromusername;

	public UserMessages(Date timestamp, String subject, boolean read, String message, Integer fromid,
			String fromusername) {
		this.timestamp = timestamp;
		this.subject = subject;
		// this.direction = direction;
		this.read = read;
		this.message = message;
		this.fromid = fromid;
		this.fromusername = fromusername;
	}

	public UserMessages(UserMessages userMessages) {
		this(userMessages.getTimestamp(), userMessages.getSubject(), userMessages.isRead(), userMessages.getMessage(),
				userMessages.getFromid(), userMessages.getFromusername());
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public boolean isRead() {
		return read;
	}

	public void setRead(boolean read) {
		this.read = read;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getFromid() {
		return fromid;
	}

	public void setFromid(Integer fromid) {
		this.fromid = fromid;
	}

	public String getFromusername() {
		return fromusername;
	}

	public void setFromusername(String fromusername) {
		this.fromusername = fromusername;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "id", "name", "type");
		serializer.include("timestamp", "fromid", "fromusername", "subject", "message");

		serializer.transform(DATE_TRANSFORMER, Date.class);
		serializer.transform(new FieldNameTransformer("datetime"), "timestamp");

		return "{\"data\":" + serializer.serialize(this) + "}";
	}

}
