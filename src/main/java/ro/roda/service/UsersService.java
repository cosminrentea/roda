package ro.roda.service;

import java.util.List;

import ro.roda.domain.Users;

public interface UsersService {

	public abstract long countAllUserses();

	public abstract void deleteUsers(Users users);

	public abstract Users findUsers(Integer id);

	public abstract List<Users> findAllUserses();

	public abstract List<Users> findUsersEntries(int firstResult, int maxResults);

	public abstract void saveUsers(Users users);

	public abstract Users updateUsers(Users users);

}
