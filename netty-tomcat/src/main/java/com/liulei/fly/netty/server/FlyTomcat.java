package com.liulei.fly.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;

/**
 * @author liu_l
 * @email: liu_lei_programmer@163.com
 * @time 2019/2/2 14:40
 * @Description: 描述: tomcat实现类，利用netty异步非阻塞IO
 */
public class FlyTomcat {

    /**
     * @author liu_l
     * @created 2019/2/2 14:43
     * @email: liu_lei_programmer@163.com
     * @param Ip
     * @param port
     * TODO 简要描述方法的作用：
            tomcat启动类
     */
    public static void start(String Ip, int port){
        //Boss服务线程
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //Worker服务线程
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        //Netty服务
        ServerBootstrap server = new ServerBootstrap();
        //option方法设置netty服务端接受队列连接的长度，如果超过则新的连接将被拒绝。windows默认200，其他默认128
        //childOption设置保持连接，类似于心跳机制，默认时7200s后则断开
        server.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                //添加管道的编译器与反编译器
                socketChannel.pipeline().addLast(new HttpRequestEncoder());
                socketChannel.pipeline().addLast(new HttpRequestDecoder());
                //绑定业务处理handler
                socketChannel.pipeline().addLast(new FlyChannelHandler());
            }
        }).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);

        try {
            //server绑定端口并启动，sync方法使服务异步等待连接
            ChannelFuture channelFuture = server.bind(Ip, port).sync();
            System.out.println("服务端启动成功");
            //服务端会在此阻塞，如果有连接进来并处理完后则服务端回关闭
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        start("192.168.1.96", 9000);
    }
}
