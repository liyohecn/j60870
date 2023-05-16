package cn.liyohe.j60870.handler;

import cn.liyohe.j60870.APdu;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;


@ChannelHandler.Sharable
public class MessageProcessHandler extends SimpleChannelInboundHandler<APdu> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, APdu msg) throws Exception {
        System.out.println(msg.getApciType());
        System.out.println(msg.getSendSeqNumber());
        System.out.println(msg.getReceiveSeqNumber());
        System.out.println(msg.toString());
    }
}
