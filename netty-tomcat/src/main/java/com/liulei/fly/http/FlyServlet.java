package com.liulei.fly.http;

/**
 * @author liu_l
 * @email: liu_lei_programmer@163.com
 * @time 2019/2/2 15:24
 * @Description: 描述: 私有servlet抽象类
 */
public abstract class FlyServlet {

    public abstract void doGet(FlyHttpRequest request, FlyHttpResponse response);

    public abstract void doPost(FlyHttpRequest request, FlyHttpResponse response);
}
