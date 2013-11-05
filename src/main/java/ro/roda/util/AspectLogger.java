package ro.roda.util;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class AspectLogger {

	private final Logger logger = LoggerFactory.getLogger(AspectLogger.class);

	// before the execution of any method with any number and type of parameters
	// in package 'ro.roda' and any sub-package execute the following code...

	@Before("execution(* ro.roda..*.*(..))")
	public void beforeMethod(JoinPoint joinPoint) {
		if (logger.isTraceEnabled()) {
			// constructing a string of the method signature, method and
			// (optional) parameters
			StringBuilder traceSB = new StringBuilder();
			traceSB.append("> ").append(joinPoint.getSignature().getDeclaringTypeName())
					.append(joinPoint.getSignature().getName());

			// Object[] args = joinPoint.getArgs();
			// StringBuilder argsSB = new StringBuilder();
			// for (int i = 0; i < args.length; i++) {
			// if (args[i] != null) {
			// argsSB.append(args[i]);
			// } else {
			// argsSB.append("null");
			// }
			// if (i < (args.length - 1)) {
			// argsSB.append(", ");
			// }
			// }
			// traceSB.append("(").append(argsSB).append(")");

			logger.trace(traceSB.toString());
		}
	}

	@After("execution(* ro.roda..*.*(..))")
	public void afterMethod(JoinPoint joinPoint) {
		if (logger.isTraceEnabled()) {
			// constructing a string of the method signature, method and
			// parameters
			StringBuilder traceSB = new StringBuilder();
			traceSB.append("< ").append(joinPoint.getSignature().getDeclaringTypeName())
					.append(joinPoint.getSignature().getName());
			logger.trace(traceSB.toString());
		}
	}

}