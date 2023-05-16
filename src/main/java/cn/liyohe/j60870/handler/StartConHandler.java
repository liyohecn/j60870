package cn.liyohe.j60870.handler;

import cn.liyohe.j60870.APdu;
import cn.liyohe.j60870.APduBuilder;
import cn.liyohe.j60870.ASdu;
import cn.liyohe.j60870.ChannelContext;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class StartConHandler extends SimpleChannelInboundHandler<APdu> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, APdu aPdu) throws Exception {
        if (aPdu.getApciType() == APdu.ApciType.STARTDT_CON) {
            int sendSeqNumber = aPdu.getSendSeqNumber();
            System.out.println("开始连接: SEND_SEQ_NUMBER:" + sendSeqNumber);

            // 发送 I 帧 总召
            ASdu aSdu = APduBuilder.buildAllCell();
            Integer receiveSeqNumber = ChannelContext.getReceiveSequenceNumber(ctx.channel());
            receiveSeqNumber = receiveSeqNumber == null ? 0 : receiveSeqNumber;
            APdu cell = new APdu(sendSeqNumber, receiveSeqNumber, APdu.ApciType.I_FORMAT, aSdu);
            ctx.writeAndFlush(cell);

        }
        ctx.fireChannelRead(aPdu);
    }
}
