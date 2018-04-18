package io.mab.guard;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Arrays;

public class MyInterceptor {

    public String preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){

        String smUserHeader = request.getHeader("sm_user");
        Cookie cookie = getTokenCookie(request, "jwt");

        return null;
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
