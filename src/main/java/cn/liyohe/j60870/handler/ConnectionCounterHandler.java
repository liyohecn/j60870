package cn.liyohe.j60870.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@ChannelHandler.Sharable
public class ConnectionCounterHandler extends ChannelInboundHandlerAdapter {

    private final AtomicInteger connections = new AtomicInteger(0);

    private final int maxConnections;

    public ConnectionCounterHandler(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        if (connections.incrementAndGet() > maxConnections) {
            ctx.close();
            return;
        }
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        connections.decrementAndGet();
        super.channelInactive(ctx);
    }
}
