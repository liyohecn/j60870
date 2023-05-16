package cn.liyohe.j60870.codec;

import cn.liyohe.j60870.APdu;
import cn.liyohe.j60870.ConnectionSettings;
import cn.liyohe.j60870.internal.HexUtils;
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
        byte[] data = new byte[length];
        System.arraycopy(bytes, 0, data, 0, length);
        byteBuf.writeBytes(data);
        System.out.println("发送数据: " + HexUtils.bytesToHex(data));
        ctx.flush();
    }
}
