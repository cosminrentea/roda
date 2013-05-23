package ro.roda.service;

import java.util.List;
import org.springframework.roo.addon.layers.service.RooService;
import ro.roda.domain.UserAuthLog;

public interface UserAuthLogService {

	public abstract long countAllUserAuthLogs();

	public abstract void deleteUserAuthLog(UserAuthLog userAuthLog);

	public abstract UserAuthLog findUserAuthLog(Long id);

	public abstract List<UserAuthLog> findAllUserAuthLogs();

	public abstract List<UserAuthLog> findUserAuthLogEntries(int firstResult, int maxResults);

	public abstract void saveUserAuthLog(UserAuthLog userAuthLog);

	public abstract UserAuthLog updateUserAuthLog(UserAuthLog userAuthLog);

}
