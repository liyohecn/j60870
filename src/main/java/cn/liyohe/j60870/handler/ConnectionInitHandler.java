package cn.liyohe.j60870.handler;

import cn.liyohe.j60870.internal.HexUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


@ChannelHandler.Sharable
public class ConnectionInitHandler extends ChannelInboundHandlerAdapter {

    private static final byte[] TESTFR_CON_BUFFER = new byte[]{0x68, 0x04, (byte) 0x83, 0x00, 0x00, 0x00};
    private static final byte[] TESTFR_ACT_BUFFER = new byte[]{0x68, 0x04, (byte) 0x43, 0x00, 0x00, 0x00};
    private static final byte[] STARTDT_ACT_BUFFER = new byte[]{0x68, 0x04, 0x07, 0x00, 0x00, 0x00};
    private static final byte[] STARTDT_CON_BUFFER = new byte[]{0x68, 0x04, 0x0b, 0x00, 0x00, 0x00};
    private static final byte[] STOPDT_ACT_BUFFER = new byte[]{0x68, 0x04, 0x13, 0x00, 0x00, 0x00};
    private static final byte[] STOPDT_CON_BUFFER = new byte[]{0x68, 0x04, 0x23, 0x00, 0x00, 0x00};

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        ByteBuf buffer = Unpooled.wrappedBuffer(STARTDT_ACT_BUFFER);
        // 直接写入数据
        ctx.writeAndFlush(buffer);
        System.out.println("发送启动链路请求:" + HexUtils.bytesToHex(STARTDT_ACT_BUFFER));
    }
}
