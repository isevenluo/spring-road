package com.sevenluo.springroad07.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @微信公众号: 七哥聊编程
 * @github: https://github.com/isevenluo
 * @gitee: https://gitee.com/isevenluo
 * @名额不多的个人微信：qige777ya
 * @description:
 * @author: 程序员七哥
 * @create: 2021-10-05 12:17
 **/
@WebServlet("/some")
public class SomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().println("Hello Servlet");
    }
}
