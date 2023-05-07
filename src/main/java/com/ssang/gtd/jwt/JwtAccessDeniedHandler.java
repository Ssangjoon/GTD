package com.ssang.gtd.jwt;

import com.ssang.gtd.exception.ErrorCode;
import com.ssang.gtd.utils.ResVO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        PrintWriter writer = response.getWriter();
        ErrorCode errorCode = ErrorCode.NOT_FOUND;
        ResVO res = ResVO.builder()
                .status(errorCode.getHttpStatus().value())
                .message(errorCode.getMessage()).build();
        try{
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            //writer.write(CmmnVar.GSON.toJson(res));
        }catch(NullPointerException e){
            logger.error("응답 메시지 작성 에러", e);
        }finally{
            if(writer != null) {
                writer.flush();
                writer.close();
            }
        }
        //response.getWriter().write(CmmnVar.GSON.toJson(res));
    }
}