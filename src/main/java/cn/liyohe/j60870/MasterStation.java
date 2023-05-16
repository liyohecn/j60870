package cn.liyohe.j60870;

import cn.liyohe.j60870.codec.MessageDecoder;
import cn.liyohe.j60870.codec.MessageEncoder;
import cn.liyohe.j60870.handler.MessageProcessHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

/**
 * 主站
 *
 * @author liyohe
 * @date 2023/05/12
 */
public class MasterStation {
    private static final int DEFAULT_PORT = 2404;
    private final String address;
    private final int port;

    private final ConnectionSettings settings;

    private final NioEventLoopGroup group = new NioEventLoopGroup(1);

    public void connect() throws InterruptedException {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, settings.getConnectionTimeout())
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        // 添加IdleStateHandler，设置超时时间为5秒
                        pipeline.addLast(new IdleStateHandler(settings.getMaxIdleTime(), 0, 0, TimeUnit.SECONDS));
                        pipeline.addLast(new MessageDecoder(settings));
                        pipeline.addLast(new MessageEncoder(settings));
                        pipeline.addLast(new MessageProcessHandler());
                    }
                })
                .connect(address, port)
                .sync();
    }

    public void close() {
        group.shutdownGracefully();
    }

    public static MasterStation.Builder builder() {
        return new MasterStation.Builder();
    }


    private MasterStation(Builder builder) {
        this.address = builder.address == null ? "127.0.0.1" : builder.address;
        this.port = builder.port == 0 ? DEFAULT_PORT : builder.port;
        this.settings = builder.settings;
    }

    public static class Builder extends CommonBuilder<Builder, MasterStation> {
        private String address;
        private int port;

        public Builder withAddress(String address) throws UnknownHostException {
            this.address = address;
            return this;
        }

        public Builder withPort(int port) {
            this.port = port;
            return this;
        }

        @Override
        public MasterStation build() throws IOException {
            return new MasterStation(this);
        }

        private Builder() {
        }
    }
}