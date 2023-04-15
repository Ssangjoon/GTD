package com.ssang.gtd.exception;


import com.ssang.gtd.utils.ResVO;
import com.ssang.gtd.utils.cons.ErrorCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        PrintWriter writer = response.getWriter();
        ErrorCode errorCode = ErrorCode.UNAUTORIZED;
        ResVO res = ResVO.builder()
                .status(errorCode.getStatus())
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
