package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.UserMessage;

public interface UserMessageService {

	public abstract long countAllUserMessages();

	public abstract void deleteUserMessage(UserMessage userMessage);

	public abstract UserMessage findUserMessage(Integer id);

	public abstract List<UserMessage> findAllUserMessages();

	public abstract List<UserMessage> findUserMessageEntries(int firstResult, int maxResults);

	public abstract void saveUserMessage(UserMessage userMessage);

	public abstract UserMessage updateUserMessage(UserMessage userMessage);

}
