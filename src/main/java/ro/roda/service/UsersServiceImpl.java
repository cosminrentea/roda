package ro.roda.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.domain.Users;

@Service
@Transactional
public class UsersServiceImpl implements UsersService {

	public long countAllUserses() {
		return Users.countUserses();
	}

	public void deleteUsers(Users users) {
		users.remove();
	}

	public Users findUsers(Integer id) {
		return Users.findUsers(id);
	}

	public List<Users> findUsersByUsernameAndEnabled(String username) {
		return Users.findUsersByUsernameAndEnabled(username, true).getResultList();
	}

	public List<Users> findAllUserses() {
		return Users.findAllUserses();
	}

	public List<Users> findUsersEntries(int firstResult, int maxResults) {
		return Users.findUsersEntries(firstResult, maxResults);
	}

	public void saveUsers(Users users) {
		users.persist();
	}

	public Users updateUsers(Users users) {
		return users.merge();
	}
}
