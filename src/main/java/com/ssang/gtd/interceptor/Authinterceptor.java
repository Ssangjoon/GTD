package com.ssang.gtd.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssang.gtd.ResultMap;
import com.ssang.gtd.user.dto.MemberDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

public class Authinterceptor implements HandlerInterceptor {

    public static final String LOG_ID = "logId";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("Authinterceptor preHandle");
        String requestURI = request.getRequestURI();
        String uuid = UUID.randomUUID().toString();
        MemberDto loginMember = (MemberDto) request.getSession().getAttribute("loginMember");

        request.setAttribute(LOG_ID, uuid);

        if (handler instanceof HandlerMethod) {
            HandlerMethod hm = (HandlerMethod) handler;
        }

        if (loginMember == null) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(new ObjectMapper().writeValueAsString(new ResultMap()
                    .setStatus(ResultMap.FAIL)
                    .setData("로그인 하지 않았습니다!")));
            //response.sendRedirect(request.getContextPath() + "");
            return false;
        }

        logger.info("REQUEST [{}][{}][{}]", uuid, requestURI, handler);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        //logger.info("postHandler [{}]", modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        String uuid = (String) request.getAttribute(LOG_ID);
        logger.info("RESPONSE [{}][{}][{}]", uuid, requestURI, handler);

        if (ex != null) {
            logger.error("afterCompletion error:", ex);
        }
    }
}
