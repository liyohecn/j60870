package cn.liyohe.j60870.handler;

import cn.liyohe.j60870.APdu;
import cn.liyohe.j60870.APduBuilder;
import cn.liyohe.j60870.ChannelContext;
import cn.liyohe.j60870.ConnectionSettings;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

@ChannelHandler.Sharable
public class IFrameHandler extends SimpleChannelInboundHandler<APdu> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, APdu aPdu) throws Exception {
        if (aPdu.getApciType() == APdu.ApciType.I_FORMAT) {
            int sendSeqNumber = aPdu.getSendSeqNumber();
            System.out.println("收到 I 帧: SEND_SEQ_NUMBER:" + sendSeqNumber);
            int receiveSequenceNumber = (sendSeqNumber + 1) % (1 << 15);
            // 发送 S 帧
            int iFrameCount = ChannelContext.getIFrameCount(ctx.channel());
            ConnectionSettings settings = ChannelContext.getSettings(ctx.channel());
            if (settings != null && iFrameCount >= settings.getMaxUnconfirmedIPdusReceived()) {
                APdu s = APduBuilder.buildSFormatPdu(receiveSequenceNumber);
                ctx.writeAndFlush(s);
                ChannelContext.clearIFrameCount(ctx.channel());
            }
        }
        ctx.fireChannelRead(aPdu);
    }
}
