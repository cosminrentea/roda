package ro.roda.audit;

import org.hibernate.envers.RevisionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import ro.roda.domain.Users;
import ro.roda.service.UsersService;

public class RodaRevisionListener implements RevisionListener {

	@Autowired
	UsersService usersService;

	@Override
	public void newRevision(Object revisionEntity) {
		RodaRevisionEntity rodaRevisionEntity = (RodaRevisionEntity) revisionEntity;
		try {
			String username = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal())
					.getUsername();
			rodaRevisionEntity.setUsername(username);
			rodaRevisionEntity.setUserid(Users.findUsersesByUsernameLike(username).getSingleResult().getId());
		} catch (Exception e) {
			// TODO refactor hard-coded string "admin" and user id value "1"
			rodaRevisionEntity.setUsername("admin");
			rodaRevisionEntity.setUserid(1);
		}

	}
}
