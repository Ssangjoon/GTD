package com.ssang.gtd.global.config;

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

    @Before("execution(* com.ssang.gtd..*(..))" + "&& !@annotation(com.ssang.gtd.global.utils.anno.NoLogging)")
    public void beforeMethodExecution(JoinPoint joinPoint) {
        logger.info("[{}] => [{} 실행]", joinPoint.getTarget().getClass().getSimpleName(), joinPoint.getSignature().getName());
    }
}
