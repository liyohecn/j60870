package cn.liyohe.j60870.handler;

import cn.liyohe.j60870.APdu;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class TestActHandler extends SimpleChannelInboundHandler<APdu> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, APdu aPdu) throws Exception {
        if (aPdu.getApciType() == APdu.ApciType.TESTFR_ACT) {
            int sendSeqNumber = aPdu.getSendSeqNumber();
            System.out.println("测试ACT: SEND_SEQ_NUMBER:" + sendSeqNumber);
        }
        ctx.fireChannelRead(aPdu);
    }
}
