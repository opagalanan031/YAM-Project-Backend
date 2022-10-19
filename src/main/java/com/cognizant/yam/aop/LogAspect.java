package com.cognizant.yam.aop;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogAspect {
	private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);
	
	@Before("execution(* com.cognizant.yam.controller.*.*(..))")
	public void logRestController_Before(JoinPoint joinPoint) {
		LOGGER.info("Before " + joinPoint.getSignature().getName() + " called");
		LOGGER.info("Args: " + Arrays.toString(joinPoint.getArgs()));
	}
	
	@After("execution(* com.cognizant.yam.controller.*.*(..))")
	public void logRestController_After(JoinPoint joinPoint) {
		LOGGER.info("After " + joinPoint.getSignature().getName() + " called");
	}
	
	@AfterReturning(pointcut="execution(* com.cognizant.yam.controller.*.*(..))", returning="result")
	public void logRestController_AfterReturning(JoinPoint joinPoint, Object result) {
		LOGGER.info("Result is: " + result);
	}
}
