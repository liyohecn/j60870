package cn.liyohe.j60870.handler;

import cn.liyohe.j60870.ChannelContext;
import cn.liyohe.j60870.ConnectionSettings;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;


@ChannelHandler.Sharable
public class ConnectionSettingsHandler extends ChannelInboundHandlerAdapter {
    private final ConnectionSettings settings;

    public ConnectionSettingsHandler(ConnectionSettings settings) {
        this.settings = settings;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 设置连接参数
        ChannelContext.setSettings(ctx.channel(), settings);
        // 初始化 I 帧计数器
        ChannelContext.clearIFrameCount(ctx.channel());

        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ChannelContext.setSettings(ctx.channel(), null);
        super.channelInactive(ctx);
    }
}
