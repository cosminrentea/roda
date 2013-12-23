package ro.roda.util;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.PreRemove;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.DeclareParents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO temporarily disabled Aspect ! if enabled, @DeclareParents must be enabled below !
// @Aspect
public class AspectPersistenceCallbacks {

	// this interface can be outside of the aspect
	public interface PersistenceCallbacks {

		@PostUpdate
		@PostPersist
		public void afterPersistOrUpdate();

		@PreRemove
		public void beforeRemove();
	};

	// this implementation can be outside of the aspect
	public static class PersistenceCallbacksImpl implements PersistenceCallbacks {

		private final Logger logger = LoggerFactory.getLogger(PersistenceCallbacksImpl.class);
		
		@PostUpdate
		@PostPersist
		public void afterPersistOrUpdate() {
			logger.trace("postPersistOrUpdateSolr");
			//TODO temporarily disabled
//			indexVariable(this);
		}

		@PreRemove
		public void beforeRemove() {
			logger.trace("preRemoveSolr");
			//TODO temporarily disabled
//			deleteIndex(this);
		}
	}
	
	//TODO this must be enabled if @Apect is enabled
//	@DeclareParents(value = "ro.roda.domain.*", defaultImpl = PersistenceCallbacksImpl.class)
	private PersistenceCallbacks implementedInterface; // the field type must be the introduced interface. It can't be a class.

	/*
	@Before("execution(* *.*(..)) && this(m)")
	void feelingMoody(Moody m) {
		System.out.println("I'm feeling " + m.getMood());
	}
	*/
}