package com.sevenluo.springroad07.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @微信公众号: 七哥聊编程
 * @github: https://github.com/isevenluo
 * @gitee: https://gitee.com/isevenluo
 * @名额不多的个人微信：qige777ya
 * @description: Spring Boot 使用 Filter3.0版本以上，通过注解即可
 * @author: 程序员七哥
 * @create: 2021-10-05 12:03
 **/
@WebFilter("/video/*")
public class SomeFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("信息已经被过滤");
        chain.doFilter(request, response);
        
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
