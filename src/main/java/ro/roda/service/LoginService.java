package ro.roda.service;

import ro.roda.transformer.Login;

public interface LoginService {

	public abstract Login findLogin(String username, String password);

}
