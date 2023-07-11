package cn.liyohe.j60870.codec;

import cn.liyohe.j60870.APdu;
import cn.liyohe.j60870.ConnectionSettings;
import cn.liyohe.j60870.internal.HexUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.util.List;

/**
 * 消息编解码器
 *
 * @author liyohe
 * @date 2023/05/24
 */
public class MessageCodec extends ByteToMessageCodec<APdu> {

    private static final Logger logger = LoggerFactory.getLogger(MessageCodec.class);

    public MessageCodec(ConnectionSettings settings) {
        this.settings = settings;
    }

    private final ConnectionSettings settings;

    @Override
    protected void encode(ChannelHandlerContext ctx, APdu aPdu, ByteBuf byteBuf) throws Exception {
        byte[] bytes = new byte[255];
        int length = aPdu.encode(bytes, settings);
        byte[] data = new byte[length];
        System.arraycopy(bytes, 0, data, 0, length);
        logger.info("发送数据: " + HexUtils.bytesToHex(data));
        byteBuf.writeBytes(data);
        ctx.flush();
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        int i = byteBuf.readableBytes();
        byte[] bytes = new byte[i];
        byteBuf.readBytes(bytes);
        logger.info("收到报文: " + HexUtils.bytesToHex(bytes));
        APdu aPdu = APdu.decode(new ByteArrayInputStream(bytes), settings);
        list.add(aPdu);
        byteBuf.release();
    }
}
