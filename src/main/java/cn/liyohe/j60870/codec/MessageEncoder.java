package cn.liyohe.j60870.codec;

import cn.liyohe.j60870.APdu;
import cn.liyohe.j60870.ConnectionSettings;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;


public class MessageEncoder extends MessageToByteEncoder<APdu> {

    public MessageEncoder(ConnectionSettings settings) {
        this.settings = settings;
    }

    private final ConnectionSettings settings;

    @Override
    protected void encode(ChannelHandlerContext ctx, APdu aPdu, ByteBuf byteBuf) throws Exception {
        byte[] bytes = new byte[255];
        int length = aPdu.encode(bytes, settings);
        byteBuf.writeBytes(bytes, 0, length);
        ctx.flush();
    }
}
