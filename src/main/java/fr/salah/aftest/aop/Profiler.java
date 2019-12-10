package fr.salah.aftest.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class Profiler {


    private Logger LOGGER = LoggerFactory.getLogger(Profiler.class);

    @Pointcut("execution(* fr.salah.aftest.controller.*.*(..))")
    public void controllerLogs() {
    }

    @Around("controllerLogs()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();

        LOGGER.info("Start - Method called : {} -> {} with args [{}]",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                joinPoint.getArgs());

        Object output = joinPoint.proceed();

        long duration = System.currentTimeMillis() - startTime;
        LOGGER.info("End - Execution time : {} milliseconds with response [{}]", duration, output);

        return output;
    }

    @AfterThrowing(pointcut = "controllerLogs()" , throwing="exc")
    public void logAfterThrowing(JoinPoint joinPoint, Exception exc) throws Throwable {
        LOGGER.error("Exception in {}.{}() with cause = {}",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                exc.getMessage() != null ? exc.getMessage() : "NULL");
    }
}
