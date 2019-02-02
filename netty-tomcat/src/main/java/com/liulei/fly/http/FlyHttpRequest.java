package com.liulei.fly.http;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.util.List;
import java.util.Map;

/**
 * @author liu_l
 * @email: liu_lei_programmer@163.com
 * @time 2019/2/2 15:22
 * @Description: 描述: 封装自己servlet的request
 */
public class FlyHttpRequest {

    private ChannelHandlerContext context;
    private HttpRequest request;

    public FlyHttpRequest(ChannelHandlerContext context, HttpRequest request){
        this.context = context;
        this.request = request;
    }

    public String getUrl(){
        return request.uri();
    }

    public HttpMethod getMethod(){
        return request.method();
    }

    public Map<String, List<String>> getParameters(){
        QueryStringDecoder decoder = new QueryStringDecoder(this.getUrl());
        return decoder.parameters();
    }

    public String getParameter(String name){
        QueryStringDecoder decoder = new QueryStringDecoder(this.getUrl());
        Map<String, List<String>> parameters = decoder.parameters();
        List<String> values = parameters.get(name);
        if(values != null){
            return values.get(0);
        }
        return "";
    }

}
