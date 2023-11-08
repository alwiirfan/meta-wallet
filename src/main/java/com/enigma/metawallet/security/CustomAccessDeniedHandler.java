package com.enigma.metawallet.security;

import com.enigma.metawallet.model.response.CommonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        CommonResponse<Object> commonResponse = CommonResponse.builder()
                .statusCode(HttpStatus.FORBIDDEN.value())
                .message(accessDeniedException.getMessage())
                .build();

        String commonResponseString = objectMapper.writeValueAsString(commonResponse);
        log.error("error : access denied : {}", accessDeniedException.getMessage());

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write(commonResponseString);
    }
}
