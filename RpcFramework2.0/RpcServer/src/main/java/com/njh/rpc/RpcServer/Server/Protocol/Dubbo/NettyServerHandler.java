package com.njh.rpc.RpcServer.Server.Protocol.Dubbo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class NettyServerHandler extends SimpleChannelInboundHandler<FullHttpRequest> {  //调用ChannelRead0退出时会自动释放资源，而ChannelInboundHandlerAdapter不会

    @Override
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        //发送信息
        channelHandlerContext.writeAndFlush(getSendByteBuf("服务端发往客户端的数据------"));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws UnsupportedEncodingException, IOException {
        //do something with msg, for example
        ByteBuf m = fullHttpRequest.content();
        String content = getMessage(m);
        //do something with content
        System.out.println("接收数据------" + content);

        DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer("some_body_content".getBytes()));
        response.headers().add(HttpHeaderNames.CONTENT_LENGTH, response.content().readableBytes());
        response.headers().add(HttpHeaderNames.CONTENT_TYPE, "text/plain");
        channelHandlerContext.writeAndFlush(response);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable cause) {
        System.out.println("Exception caught in netty channel."+cause);
        channelHandlerContext.close();
    }

    /*
     * 将字节UTF-8编码返回字符串
     */
    private String getMessage (ByteBuf buf){
        byte[] con = new byte[buf.readableBytes()];
        buf.readBytes(con);
        try {
            return new String(con, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * 将Sting转化为UTF-8编码的字节
     */
    private ByteBuf getSendByteBuf (String message) throws UnsupportedEncodingException {
        byte[] req = message.getBytes("UTF-8");
        ByteBuf pingMessage = Unpooled.buffer();
        pingMessage.writeBytes(req);
        return pingMessage;
    }

}