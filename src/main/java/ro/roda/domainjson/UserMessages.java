package ro.roda.domainjson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.UserMessage;
import flexjson.JSONSerializer;

@Configurable
public class UserMessages extends JsonInfo {

	public static String toJsonArray(Collection<UserMessages> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "id", "name", "type");
		serializer.include("timestamp", "subject", "direction", "read", "message");

		serializer.transform(DATE_TRANSFORMER, Date.class);

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<UserMessages> findUserMessages(Integer id) {
		List<UserMessages> userMessages = new ArrayList<UserMessages>();

		List<UserMessage> incomingMessages = findUserIncomingMessages(id);

		if (incomingMessages != null && incomingMessages.size() > 0) {
			Iterator<UserMessage> incomingMessagesIterator = incomingMessages.iterator();
			while (incomingMessagesIterator.hasNext()) {
				UserMessage message = incomingMessagesIterator.next();
				userMessages.add(new UserMessages(message.getTimestamp(), message.getSubject(), "incoming", message
						.isRead(), message.getMessage()));
			}
		}

		List<UserMessage> outcomingMessages = findUserIncomingMessages(id);

		if (outcomingMessages != null && outcomingMessages.size() > 0) {
			Iterator<UserMessage> outcomingMessagesIterator = outcomingMessages.iterator();
			while (outcomingMessagesIterator.hasNext()) {
				UserMessage message = outcomingMessagesIterator.next();
				userMessages.add(new UserMessages(null, null, "outcoming", false, message.getMessage()));
			}
		}

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

	private String direction;

	private boolean read;

	private String message;

	public UserMessages(Date timestamp, String subject, String direction, boolean read, String message) {
		this.timestamp = timestamp;
		this.subject = subject;
		this.direction = direction;
		this.read = read;
		this.message = message;
	}

	public UserMessages(UserMessages userMessages) {
		this(userMessages.getTimestamp(), userMessages.getSubject(), userMessages.getDirection(),
				userMessages.isRead(), userMessages.getMessage());
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

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
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

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "id", "name", "type");
		serializer.include("timestamp", "subject", "direction", "read", "message");

		serializer.transform(DATE_TRANSFORMER, Date.class);

		return "{\"data\":" + serializer.serialize(this) + "}";
	}

}
