package ro.roda.domainjson;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Configurable;

import ro.roda.domain.UserAuthLog;
import flexjson.JSONSerializer;

@Configurable
public class UserActivities extends JsonInfo {

	public static String toJsonArray(Collection<UserActivities> collection) {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "id", "name");
		serializer.include("timestamp", "type", "details", "error", "errormessage", "errordetails");

		serializer.transform(DATE_TRANSFORMER, Date.class);

		return "{\"data\":" + serializer.serialize(collection) + "}";
	}

	public static List<UserActivities> findUserActivities(Integer id) {
		List<UserActivities> userActivities = new ArrayList<UserActivities>();

		List<UserAuthLog> activities = findUserAuthLogs(id);

		if (activities != null && activities.size() > 0) {
			Iterator<UserAuthLog> activitiesIterator = activities.iterator();
			while (activitiesIterator.hasNext()) {
				UserAuthLog activity = activitiesIterator.next();
				userActivities.add(new UserActivities(activity.getAuthAttemptedAt().getTime(), activity.getAction(),
						activity.getDetails(), activity.getError(), activity.getErrorMessage(), activity
								.getErrorDetails()));
			}
		}

		return userActivities;
	}

	public static List<UserAuthLog> findUserAuthLogs(int id) {
		return UserAuthLog.entityManager()
				.createQuery("SELECT o FROM UserAuthLog o WHERE o.userId = " + id, UserAuthLog.class).getResultList();
	}

	private Date timestamp;

	private String type;

	private String details;

	private String error;

	private String errorMessage;

	private String errorDetails;

	public UserActivities(Date timestamp, String type, String details, String error, String errorMessage,
			String errorDetails) {
		this.timestamp = timestamp;
		this.type = type;
		this.details = details;
		this.error = error;
		this.errorMessage = errorMessage;
		this.errorDetails = errorDetails;
	}

	public UserActivities(UserActivities userActivities) {
		this(userActivities.getTimestamp(), userActivities.getType(), userActivities.getDetails(), userActivities
				.getError(), userActivities.getErrorMessage(), userActivities.getErrorDetails());
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorDetails() {
		return errorDetails;
	}

	public void setErrorDetails(String errorDetails) {
		this.errorDetails = errorDetails;
	}

	public String toJson() {
		JSONSerializer serializer = new JSONSerializer();

		serializer.exclude("*.class", "id", "name");
		serializer.include("timestamp", "type", "details", "error", "errormessage", "errordetails");

		serializer.transform(DATE_TRANSFORMER, Date.class);

		return "{\"data\":" + serializer.serialize(this) + "}";
	}

}
