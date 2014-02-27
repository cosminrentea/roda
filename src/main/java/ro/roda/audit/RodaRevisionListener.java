package ro.roda.audit;

import org.hibernate.envers.RevisionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import ro.roda.service.UsersService;

public class RodaRevisionListener implements RevisionListener {

	@Autowired
	UsersService usersService;

	@Override
	public void newRevision(Object revisionEntity) {
		RodaRevisionEntity rodaRevisionEntity = (RodaRevisionEntity) revisionEntity;
		try {
			rodaRevisionEntity.setUsername(SecurityContextHolder.getContext().getAuthentication().getPrincipal()
					.toString());
		} catch (Exception e) {
			rodaRevisionEntity.setUsername("admin");
		}

	}
}
