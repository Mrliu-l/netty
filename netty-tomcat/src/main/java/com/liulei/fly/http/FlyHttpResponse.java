package com.liulei.fly.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

/**
 * @author liu_l
 * @email: liu_lei_programmer@163.com
 * @time 2019/2/2 15:23
 * @Description: 描述: 封装自己servlet的response
 */
public class FlyHttpResponse {

    private ChannelHandlerContext context;
    private HttpRequest request;

    public FlyHttpResponse(ChannelHandlerContext context, HttpRequest request){
        this.context = context;
        this.request = request;
    }

    public void write(String message){
        try {
            ByteBuf byteBuf = Unpooled.wrappedBuffer(message.getBytes("UTF-8"));
            HttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, byteBuf);
            //数据类型
            response.headers().add(HttpHeaderNames.CONTENT_TYPE, "text/html; charset=UTF-8");
            //返回数据长度

            response.headers().add(HttpHeaderNames.CONTENT_LENGTH, ((DefaultFullHttpResponse) response).content().readableBytes());
            //过期时间
            response.headers().add(HttpHeaderNames.EXPIRES, 0);
            //判断连接是否可以一直保持
            if(HttpUtil.isKeepAlive(response)){
                response.headers().add(HttpHeaderNames.CONNECTION , HttpHeaderValues.KEEP_ALIVE);
            }
            context.writeAndFlush(response);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
