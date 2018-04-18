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

        if(smUserHeader == null){
            return "NOT_AUTHORIZED";
        }else{
            if(smUserHeader.isEmpty()){
                return "LOGIN";
            }else{
                if(cookie == null || cookie.getValue().isEmpty()){
                    return "LOGIN";
                }else{
                    if(!cookie.getValue().equals(smUserHeader)){
                        return "LOGIN";
                    }
                }
            }
        }

        return "OK";
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
