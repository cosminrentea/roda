package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.UserAuthLog;

@Service
@Transactional
public class UserAuthLogServiceImpl implements UserAuthLogService {

	public long countAllUserAuthLogs() {
		return UserAuthLog.countUserAuthLogs();
	}

	public void deleteUserAuthLog(UserAuthLog userAuthLog) {
		userAuthLog.remove();
	}

	public UserAuthLog findUserAuthLog(Long id) {
		return UserAuthLog.findUserAuthLog(id);
	}

	public List<UserAuthLog> findAllUserAuthLogs() {
		return UserAuthLog.findAllUserAuthLogs();
	}

	public List<UserAuthLog> findUserAuthLogEntries(int firstResult, int maxResults) {
		return UserAuthLog.findUserAuthLogEntries(firstResult, maxResults);
	}

	public void saveUserAuthLog(UserAuthLog userAuthLog) {
		userAuthLog.persist();
	}

	public UserAuthLog updateUserAuthLog(UserAuthLog userAuthLog) {
		return userAuthLog.merge();
	}
}
