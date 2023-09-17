package com.atstudy;

import com.atstudy.servlet.Servlet;

import java.util.ResourceBundle;
import java.util.Scanner;

public class Tomcat {

    public static void main(String[] args) throws Exception {

        // 用户从浏览器输出人url
        Scanner s = new Scanner(System.in);

        System.out.println("请输入url:");
        String url = s.nextLine();

        // 怎么知道这个url到底对应的是哪个程序
        // 现在想要的效果是。用户输入/hello 可以执行这个HelloServlet里面的service方法
        // 利用反射,获取到url之后根据这个url获取到对应的类名,通过反射创建对象，调用它的service方法
        String className = ResourceBundle.getBundle("web").getString("/hello");
        Class<?> aClass = Class.forName(className);

        // 通过配置文件中的全类名创建出来的对象是Servlet接口的实现类，所以可以强转
        Servlet servlet = (Servlet) aClass.getDeclaredConstructor().newInstance();
        servlet.service();
    }

}
