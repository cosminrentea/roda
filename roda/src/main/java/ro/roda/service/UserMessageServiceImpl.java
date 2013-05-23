package ro.roda.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.roda.domain.UserMessage;


@Service
@Transactional
public class UserMessageServiceImpl implements UserMessageService {

	public long countAllUserMessages() {
        return UserMessage.countUserMessages();
    }

	public void deleteUserMessage(UserMessage userMessage) {
        userMessage.remove();
    }

	public UserMessage findUserMessage(Integer id) {
        return UserMessage.findUserMessage(id);
    }

	public List<UserMessage> findAllUserMessages() {
        return UserMessage.findAllUserMessages();
    }

	public List<UserMessage> findUserMessageEntries(int firstResult, int maxResults) {
        return UserMessage.findUserMessageEntries(firstResult, maxResults);
    }

	public void saveUserMessage(UserMessage userMessage) {
        userMessage.persist();
    }

	public UserMessage updateUserMessage(UserMessage userMessage) {
        return userMessage.merge();
    }
}
