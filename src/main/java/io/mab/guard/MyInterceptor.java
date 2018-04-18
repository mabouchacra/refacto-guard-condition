package io.mab.guard;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Arrays;

public class MyInterceptor {

    public static final String HEADER_SM_USER = "sm_user";
    public static final String COOKIE_JWT_NAME = "jwt";

    public String preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){

        String smUserHeader = request.getHeader("sm_user");
        Cookie cookie = getTokenCookie(request, "jwt");

        if(isNotAuthorized(smUserHeader)){
            return "NOT_AUTHORIZED";
        }

        if (isLoginRequired(smUserHeader, cookie)) {
            return "LOGIN";
        }

        return "OK";
    }

    private boolean isLoginRequired(String smUserHeader, Cookie cookie) {
        return smUserHeader.isEmpty() || cookie == null || cookie.getValue().isEmpty() || !cookie.getValue().equals(smUserHeader);
    }

    private boolean isNotAuthorized(String smUserHeader) {
        return smUserHeader == null;
    }

    private static Cookie getTokenCookie(HttpServletRequest request, String cookieName){
        Cookie cookie = null;
        if (request.getCookies() != null) {
            cookie = Arrays.stream(request.getCookies())
                           .filter(c -> cookieName.equalsIgnoreCase(c.getName()))
                           .findFirst()
                           .orElse(null);
        }
        return cookie;
    }
}
