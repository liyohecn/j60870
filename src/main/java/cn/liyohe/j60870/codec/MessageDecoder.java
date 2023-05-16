package cn.liyohe.j60870.codec;

import cn.liyohe.j60870.APdu;
import cn.liyohe.j60870.ConnectionSettings;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.ByteArrayInputStream;
import java.util.List;


public class MessageDecoder extends ByteToMessageDecoder {
    public MessageDecoder(ConnectionSettings settings) {
        this.settings = settings;
    }

    private final ConnectionSettings settings;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        int i = byteBuf.readableBytes();
        byte[] bytes = new byte[i];
        byteBuf.readBytes(bytes);
        APdu aPdu = APdu.decode(new ByteArrayInputStream(bytes), settings);
        list.add(aPdu);
    }
}
