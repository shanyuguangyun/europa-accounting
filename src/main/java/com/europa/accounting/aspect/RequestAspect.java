package com.europa.accounting.aspect;

import com.europa.accounting.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * @author fengwen
 * @date 2021-10-14
 * 登陆切面
 */
@Slf4j
@Aspect
@Component
public class RequestAspect {

    @Value("${ignore.path}")
    private List<String> ignorePath;

    @Pointcut("bean(*Controller)")
    public void requestPoint() {

    }

    @Before("requestPoint()")
    public void doBefore(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new IllegalArgumentException("解析请求错误");
        }
        HttpServletRequest request = attributes.getRequest();

        log.info("用户操作日志:\n  url:{}\n  ip:{}\n  参数:{}",
                request.getRequestURI(),
                request.getRemoteAddr(),
                joinPoint.getArgs());

        String url = request.getRequestURI();
        if (!ignorePath.contains(url)) {
            // check token
            String token = request.getHeader("token");
            if (StringUtils.isEmpty(token)) {
                throw new ApiException("token不能为空");
            }
            if (!"TEMP_TOKEN".equals(token)) {
                throw new ApiException("token已过期，请重新登陆");
            }
        }
    }

    // token check


}
