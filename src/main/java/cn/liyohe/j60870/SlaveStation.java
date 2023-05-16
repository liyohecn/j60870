package cn.liyohe.j60870;


import cn.liyohe.j60870.handler.ConnectionCounterHandler;
import cn.liyohe.j60870.handler.WhitelistHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 从站
 *
 * @author liyohe
 * @date 2023/05/12
 */
public class SlaveStation {

    private final int port;
    private final String bindAddr;
    private final int maxConnections;
    private final List<String> whitelist;
    private final ConnectionSettings settings;

    private final NioEventLoopGroup boosGroup;

    private final NioEventLoopGroup workerGroup;

    private SlaveStation(Builder builder) {
        this.bindAddr = builder.bindAddr == null ? "0.0.0.0" : builder.bindAddr;
        this.port = builder.port;
        this.whitelist = builder.whitelist;
        this.maxConnections = builder.maxConnections;
        this.settings = new ConnectionSettings();
        this.boosGroup = new NioEventLoopGroup(builder.bossThreads);
        this.workerGroup = new NioEventLoopGroup(builder.workerThreads);
    }

    public void start() throws IOException, InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boosGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, settings.getConnectionTimeout())

                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        pipeline.addLast(new IdleStateHandler(settings.getMaxIdleTime(), 0, 0, TimeUnit.SECONDS));
                        if (whitelist != null && whitelist.size() > 0) {
                            pipeline.addLast(new WhitelistHandler(whitelist));
                        }
                        pipeline.addLast(new ConnectionCounterHandler(maxConnections));
                    }
                })
                .bind(bindAddr, port)
                .sync();
    }

    public void stop() {
        boosGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }


    public static Builder builder() {
        return new Builder();
    }


    public static class Builder extends CommonBuilder<Builder, SlaveStation> {
        private int port = 2404;
        private String bindAddr;

        private List<String> whitelist;

        private int maxConnections = 100;

        private int bossThreads = 1;

        private int workerThreads = 2;

        public Builder withPort(int port) {
            this.port = port;
            return this;
        }

        public Builder withBindAddr(String addr) {
            this.bindAddr = addr;
            return this;
        }

        public Builder withWhiteIps(List<String> whitelist) {
            this.whitelist = whitelist;
            return this;
        }

        public Builder withBossThreads(int bossThreads) {
            this.bossThreads = bossThreads;
            return this;
        }

        public Builder withWorkerThreads(int workerThreads) {
            this.workerThreads = workerThreads;
            return this;
        }

        public Builder withMaxConnections(int maxConnections) {
            this.maxConnections = maxConnections;
            return this;
        }

        @Override
        public SlaveStation build() throws IOException {
            return new SlaveStation(this);
        }

        private Builder() {
        }
    }
}
