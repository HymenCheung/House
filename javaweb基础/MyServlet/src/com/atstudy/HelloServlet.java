package com.atstudy;

import com.atstudy.servlet.Servlet;

public class HelloServlet implements Servlet {
    @Override
    public void service() {
        System.out.println("hello world");
    }
}
