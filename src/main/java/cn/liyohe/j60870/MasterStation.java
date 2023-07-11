package cn.liyohe.j60870;

import cn.liyohe.j60870.codec.MessageCodec;
import cn.liyohe.j60870.handler.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

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

    private Bootstrap bootstrap;

    private final NioEventLoopGroup group = new NioEventLoopGroup(1);

    private MasterStation(Builder builder) {
        this.address = builder.address == null ? "127.0.0.1" : builder.address;
        this.port = builder.port == 0 ? DEFAULT_PORT : builder.port;
        this.settings = builder.settings;
        initNetty();
    }

    private void initNetty() {
        bootstrap = new Bootstrap().group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, settings.getConnectionTimeout())
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        // 设置 粘包/拆包 处理器
                        pipeline.addLast(new LengthFieldFrameHandler());
                        // 添加IdleStateHandler，设置超时时间为5秒
                        pipeline.addLast(new IdleStateHandler(settings.getMaxIdleTime(), settings.getMaxIdleTime(), settings.getMaxIdleTime(), TimeUnit.MILLISECONDS));
                        // 设置编解码器
                        pipeline.addLast(new MessageCodec(settings));
                        pipeline.addLast(new ConnectionSettingsHandler(settings));
                        pipeline.addLast(new ConnectionInitHandler());
                        pipeline.addLast(new StartConHandler());
                        pipeline.addLast(new StartActHandler());
                        pipeline.addLast(new StopConHandler());
                        pipeline.addLast(new StopActHandler());
                        pipeline.addLast(new TestConHandler());
                        pipeline.addLast(new TestActHandler());
                        pipeline.addLast(new IFrameHandler());
                        pipeline.addLast(new SFrameHandler());
                    }
                });
    }

    public void close() {
        group.shutdownGracefully();
    }

    public static MasterStation.Builder builder() {
        return new MasterStation.Builder();
    }

    public void connect() throws InterruptedException {
        bootstrap
                .connect(address, port)
                .sync()
                // 监听关闭连接事件
                .channel().closeFuture().addListener(f -> {
                    group.shutdownGracefully();
                });
    }

    public static class Builder extends CommonBuilder<Builder, MasterStation> {
        private String address;
        private int port;

        public Builder withAddress(String address) {
            this.address = address;
            return this;
        }

        public Builder withPort(int port) {
            this.port = port;
            return this;
        }

        @Override
        public MasterStation build() {
            return new MasterStation(this);
        }

        private Builder() {
        }
    }
}
