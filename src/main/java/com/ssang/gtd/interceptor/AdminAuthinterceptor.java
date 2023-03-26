package com.ssang.gtd.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssang.gtd.ResultMap;
import com.ssang.gtd.config.MvcConfiguration;
import com.ssang.gtd.user.dto.MemberDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
public class AdminAuthinterceptor implements HandlerInterceptor {
    private static final Logger log = LogManager.getLogger(MvcConfiguration.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        log.debug("AdminAuthinterceptor.preHandle() 호출됨!");

        // 로그인 여부 검사
        HttpSession session = request.getSession(); // http session 객체를 얻어온다.
        MemberDto loginUser = (MemberDto) session.getAttribute("loginUser"); // session 객체에서 loginUser로 저장된
        // member 객체를 꺼낸다.


        if (loginUser == null) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(new ObjectMapper().writeValueAsString(
                    new ResultMap().setStatus(ResultMap.FAIL).setData("관리자 페이지 접근 권한이 없습니다.")));
            response.sendRedirect(request.getContextPath() + "/jaewon/admin/login/index.html");
            return false;
        }
        /*if (!loginUser.getMembLevel().equals("100")) {
            // 로그인을 하지 않았으면 오류 메시지를 JSON 형식으로 직접 응답한다.
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(new ObjectMapper().writeValueAsString(
                    new ResultMap().setStatus(ResultMap.FAIL).setData("관리자 페이지 접근 권한이 없습니다.")));
            response.sendRedirect(request.getContextPath() + "/jaewon/admin/login/index.html");
            return false;// 페이지 컨트롤러를 실행하지 말고 즉시 응답하라!
        }*/

        return true; // 로그인 된 상태라면, 계속 진행하라! (요청한 페이지 컨트롤러의 메서드를 호출하라!)
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        log.trace("postHandle() 호출됨!");
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}