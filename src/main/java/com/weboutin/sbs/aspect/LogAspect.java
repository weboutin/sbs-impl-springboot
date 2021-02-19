package com.weboutin.sbs.aspect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.weboutin.sbs.utils.Utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

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

    @Around("controllerAspect()")
    public Object Authenfication(ProceedingJoinPoint point) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        Cookie[] cookies = request.getCookies();
        String sessionCookieStr = null;
        for (Cookie c : cookies) {
            if (c.getName().equals("session-id")) {
                sessionCookieStr = c.getValue();
            }
        }
        if (sessionCookieStr == null) {
            return Utils.buildResponse(-1, "无访问权限", new HashMap<>());
        }

        JSONObject session = Utils.parseSessionCookie(sessionCookieStr);
        Integer userId = session.optInt("userId");
        List<Object> list = new ArrayList<Object>();
        for (Object arg : point.getArgs()) {
            if (arg != null) {
                list.add(arg);
            }
        }
        list.add(userId);
        return point.proceed(list.toArray());
    }

}
