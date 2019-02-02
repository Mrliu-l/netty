package com.liulei.fly.netty.server;

import com.liulei.fly.http.FlyHttpRequest;
import com.liulei.fly.http.FlyHttpResponse;
import com.liulei.fly.servlet.MyServlet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;

/**
 * @author liu_l
 * @email: liu_lei_programmer@163.com
 * @time 2019/2/2 15:06
 * @Description: 描述: 实现提供的handler
 */
public class FlyChannelHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(msg instanceof HttpRequest){
            HttpRequest httpRequest = (HttpRequest) msg;
            FlyHttpRequest flyHttpRequest = new FlyHttpRequest(ctx, httpRequest);
            FlyHttpResponse flyHttpResponse = new FlyHttpResponse(ctx, httpRequest);
            new MyServlet().doGet(flyHttpRequest, flyHttpResponse);
        }
    }
}
