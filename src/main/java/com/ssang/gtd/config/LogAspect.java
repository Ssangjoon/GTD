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
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        logger.info("{} => {} 실행", className,methodName);
    }
    //@Before("execution(* com.ssang.gtd..*(..))")
    /*public Object logging(ProceedingJoinPoint pjp) throws Throwable {
        logger.info("{} => {} 실행됨", pjp.getSignature().getDeclaringTypeName(),pjp.getSignature().getName());
        Object result = pjp.proceed();
        logger.info("{} => {} 종료됨", pjp.getSignature().getDeclaringTypeName(),pjp.getSignature().getName());
        return result;
    }*/
}
