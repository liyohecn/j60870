package cn.liyohe.j60870.handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.net.InetSocketAddress;
import java.util.List;


@ChannelHandler.Sharable
public class WhitelistHandler extends ChannelInboundHandlerAdapter {

    List<String> whiteList;

    public WhitelistHandler(List<String> whiteList) {
        this.whiteList = whiteList;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String ip = ((InetSocketAddress) ctx.channel().remoteAddress()).getHostString();
        if (!whiteList.contains(ip)) {
            // 不在白名单中，关闭连接
            ctx.close();
            return;
        }
        super.channelActive(ctx);
    }
}
