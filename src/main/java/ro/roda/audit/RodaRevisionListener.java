package ro.roda.audit;

import org.hibernate.envers.RevisionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import ro.roda.service.UsersService;

public class RodaRevisionListener implements RevisionListener {

	@Autowired
	UsersService usersService;

	@Override
	public void newRevision(Object revisionEntity) {
		RodaRevisionEntity rodaRevisionEntity = (RodaRevisionEntity) revisionEntity;
		try {
			rodaRevisionEntity.setUsername(((User) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal()).getUsername());
		} catch (Exception e) {
			//TODO refactor hard-coded string "admin"
			rodaRevisionEntity.setUsername("admin");
		}

	}
}
