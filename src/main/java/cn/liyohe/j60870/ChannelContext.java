package cn.liyohe.j60870;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * channel 属性工具类
 *
 * @author liyohe
 * @date 2023/05/16
 */
public class ChannelContext {

    private static final AttributeKey<ConnectionSettings> SETTINGS = AttributeKey.valueOf("settings");

    private static final AttributeKey<Integer> RECEIVE_SEQ_NUMBER = AttributeKey.valueOf("receiveSequenceNumber");

    private static final AttributeKey<Integer> SEND_SEQ_NUMBER = AttributeKey.valueOf("sendSequenceNumber");

    private static final AttributeKey<Integer> ACKNOWLEDGE_SEND_SEQ_NUMBER = AttributeKey.valueOf("acknowledgedSendSequenceNumber");

    private static final AttributeKey<AtomicInteger> I_FRAME_COUNT = AttributeKey.valueOf("iFrameCount");

    public static Integer getReceiveSequenceNumber(Channel channel) {
        return channel.attr(RECEIVE_SEQ_NUMBER).get();
    }

    public static void setReceiveSequenceNumber(Channel channel, int acknowledgedSendSequenceNumber) {
        channel.attr(RECEIVE_SEQ_NUMBER).set(acknowledgedSendSequenceNumber);
    }


    public static Integer getSendSequenceNumber(Channel channel) {
        return channel.attr(SEND_SEQ_NUMBER).get();
    }

    public static void setSendSequenceNumber(Channel channel, int acknowledgedSendSequenceNumber) {
        channel.attr(SEND_SEQ_NUMBER).set(acknowledgedSendSequenceNumber);
    }

    public static Integer getAcknowledgedSendSequenceNumber(Channel channel) {
        return channel.attr(ACKNOWLEDGE_SEND_SEQ_NUMBER).get();
    }

    public static void setAcknowledgedSendSequenceNumber(Channel channel, int acknowledgedSendSequenceNumber) {
        channel.attr(ACKNOWLEDGE_SEND_SEQ_NUMBER).set(acknowledgedSendSequenceNumber);
    }


    private static int sequenceNumberDiff(int number, int ackNumber) {
        // would hold true: ackNumber <= number (without mod 2^15)
        return ackNumber > number ? ((1 << 15) - ackNumber) + number : number - ackNumber;
    }

    public static ConnectionSettings getSettings(Channel channel) {
        return channel.attr(SETTINGS).get();
    }


    public static void setSettings(Channel channel, ConnectionSettings settings) {
        channel.attr(SETTINGS).set(settings);
    }

    public static int getIFrameCount(Channel channel) {
        return channel.attr(I_FRAME_COUNT).get().incrementAndGet();
    }

    public static void clearIFrameCount(Channel channel) {
        if (channel.attr(I_FRAME_COUNT).get()==null){
            channel.attr(I_FRAME_COUNT).set(new AtomicInteger(0));
        }else {
            channel.attr(I_FRAME_COUNT).get().set(0);
        }
    }

}
