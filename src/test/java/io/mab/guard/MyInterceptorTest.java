package io.mab.guard;

import org.springframework.mock.web.MockHttpServletRequest;

public class MyInterceptorTest {

    private final MyInterceptor interceptor;

    public MyInterceptorTest(){
        MyInterceptor myInterceptor = new MyInterceptor();

        this.interceptor = myInterceptor;
    }
}