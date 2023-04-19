package com.ssang.gtd.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Before("execution(* com.ssang.gtd..*(..))")
    public void beforeMethodExecution(JoinPoint joinPoint) {
        logger.info("[{}] => [{} 실행]", joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName());
    }
}
