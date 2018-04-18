package io.mab.guard;

import static io.mab.guard.MyInterceptor.COOKIE_JWT_NAME;
import static io.mab.guard.MyInterceptor.HEADER_SM_USER;
import static org.assertj.core.api.Assertions.assertThat;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

public class MyInterceptorTest {

    private final MyInterceptor interceptor;

    public MyInterceptorTest(){
        MyInterceptor myInterceptor = new MyInterceptor();

        this.interceptor = myInterceptor;
    }

    @Test
    public void should_return_not_authorized_if_sm_user_absent(){
        HttpServletRequest request = new MockHttpServletRequest();
        HttpServletResponse response = new MockHttpServletResponse();

        String page = interceptor.preHandle(request, response, null);
        assertThat(page).isEqualTo("NOT_AUTHORIZED");
    }

    @Test
    public void should_return_login_if_sm_user_empty(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(HEADER_SM_USER, "");

        HttpServletResponse response = new MockHttpServletResponse();

        String page = interceptor.preHandle(request, response, null);
        assertThat(page).isEqualTo("LOGIN");
    }

    @Test
    public void should_return_login_if_cookie_null_or_empty(){

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(HEADER_SM_USER, "john");

        HttpServletResponse response = new MockHttpServletResponse();

        String page = interceptor.preHandle(request, response, null);
        assertThat(page).isEqualTo("LOGIN");

        request.setCookies(new Cookie(COOKIE_JWT_NAME, ""));

        page = interceptor.preHandle(request, response, null);
        assertThat(page).isEqualTo("LOGIN");
    }

    @Test
    public void should_return_login_if_cookie_and_sm_user_different(){

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(HEADER_SM_USER, "john");
        request.setCookies(new Cookie(COOKIE_JWT_NAME, "campbell"));

        HttpServletResponse response = new MockHttpServletResponse();

        String page = interceptor.preHandle(request, response, null);
        assertThat(page).isEqualTo("LOGIN");
    }

    @Test
    public void should_return_ok_if_cookie_and_sm_user_equals(){

        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader(HEADER_SM_USER, "john");
        request.setCookies(new Cookie(COOKIE_JWT_NAME, "john"));

        HttpServletResponse response = new MockHttpServletResponse();

        String page = interceptor.preHandle(request, response, null);
        assertThat(page).isEqualTo("OK");
    }
}