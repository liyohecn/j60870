package cn.liyohe.j60870.handler;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * 粘包/拆包 处理程序
 *
 * @author liyohe
 * @date 2023/05/24
 */
public class LengthFieldFrameHandler extends LengthFieldBasedFrameDecoder {

    /**
     * 最大帧长度
     */
    private static final int MAX_FRAME_LENGTH = 255;

    public LengthFieldFrameHandler() {
        super(MAX_FRAME_LENGTH, 1, 1, 0, 0);
    }
}
