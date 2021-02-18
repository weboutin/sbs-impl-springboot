package com.weboutin.sbs.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

    @Pointcut("execution(* com.weboutin.sbs..controller.*.*(..))")
    public void controllerAspect() {
    }

    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) throws InterruptedException {
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof String) {
                System.out.println(arg);
            }
        }
    }

    @AfterReturning(pointcut = "controllerAspect()", returning = "retVal")
    public void doAccessCheck(Object retVal) {
        System.out.println(retVal);
    }

}
