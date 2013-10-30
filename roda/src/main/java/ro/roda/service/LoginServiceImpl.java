package ro.roda.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ro.roda.transformer.Login;

@Service
@Transactional
public class LoginServiceImpl implements LoginService {

	public Login findLogin(String username, String password) {
		return Login.findLogin(username, password);
	}
}
