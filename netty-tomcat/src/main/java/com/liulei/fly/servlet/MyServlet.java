package com.liulei.fly.servlet;

import com.liulei.fly.http.FlyHttpRequest;
import com.liulei.fly.http.FlyHttpResponse;
import com.liulei.fly.http.FlyServlet;

/**
 * @author liu_l
 * @email: liu_lei_programmer@163.com
 * @time 2019/2/2 15:28
 * @Description: 描述:
 */
public class MyServlet extends FlyServlet {
    @Override
    public void doGet(FlyHttpRequest request, FlyHttpResponse response) {
        System.out.println("服务器收到请求");
        response.write(request.getParameter("name"));
    }

    @Override
    public void doPost(FlyHttpRequest request, FlyHttpResponse response) {
        doGet(request, response);
    }
}
