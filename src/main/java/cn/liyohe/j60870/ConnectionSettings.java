package cn.liyohe.j60870;

public class ConnectionSettings {
    /**
     * 消息片段超时
     */
    private int messageFragmentTimeout;

    /**
     * 床字段长度
     */
    private int cotFieldLength;
    /**
     * 常见地址字段长度
     */
    private int commonAddressFieldLength;
    /**
     * ioa字段长度
     */
    private int ioaFieldLength;

    /**
     * 没有收到ack最大时间
     */
    private int maxTimeNoAckReceived;
    /**
     * 没有发送ack最大时间
     */
    private int maxTimeNoAckSent;
    /**
     * 最大空闲时间
     */
    private int maxIdleTime;

    /**
     * 连接超时时间
     */
    private int connectionTimeout;

    /**
     * 收到多少 I 帧 回复一帧 S 帧
     */
    private int maxUnconfirmedIPdusReceived;
    /**
     * 发送确认变量的最大差异发送序列号的数目(最小值为1，最大值为32767)
     */
    private int maxNumOfOutstandingIPdus;


    public ConnectionSettings() {
        this.messageFragmentTimeout = 5_000;
        this.cotFieldLength = 2;
        this.commonAddressFieldLength = 2;
        this.ioaFieldLength = 3;
        this.connectionTimeout = 5_000;
        this.maxTimeNoAckReceived = 15_000;
        this.maxTimeNoAckSent = 10_000;
        this.maxIdleTime = 20_000;
        this.maxUnconfirmedIPdusReceived = 8;
        this.maxNumOfOutstandingIPdus = 12;
    }

    public ConnectionSettings(ConnectionSettings connectionSettings) {
        messageFragmentTimeout = connectionSettings.messageFragmentTimeout;
        cotFieldLength = connectionSettings.cotFieldLength;
        commonAddressFieldLength = connectionSettings.commonAddressFieldLength;
        ioaFieldLength = connectionSettings.ioaFieldLength;
        maxTimeNoAckReceived = connectionSettings.maxTimeNoAckReceived;
        maxTimeNoAckSent = connectionSettings.maxTimeNoAckSent;
        maxIdleTime = connectionSettings.maxIdleTime;
        connectionTimeout = connectionSettings.connectionTimeout;
        maxUnconfirmedIPdusReceived = connectionSettings.maxUnconfirmedIPdusReceived;
        maxNumOfOutstandingIPdus = connectionSettings.maxNumOfOutstandingIPdus;
    }


    public int getMessageFragmentTimeout() {
        return messageFragmentTimeout;
    }

    public int getCotFieldLength() {
        return cotFieldLength;
    }

    public int getCommonAddressFieldLength() {
        return commonAddressFieldLength;
    }

    public int getIoaFieldLength() {
        return ioaFieldLength;
    }

    public int getMaxTimeNoAckReceived() {
        return maxTimeNoAckReceived;
    }

    public int getMaxTimeNoAckSent() {
        return maxTimeNoAckSent;
    }

    public int getMaxIdleTime() {
        return maxIdleTime;
    }

    public int getMaxUnconfirmedIPdusReceived() {
        return maxUnconfirmedIPdusReceived;
    }

    public int getMaxNumOfOutstandingIPdus() {
        return this.maxNumOfOutstandingIPdus;
    }

    public int getConnectionTimeout() {
        return this.connectionTimeout;
    }

    public void setMessageFragmentTimeout(int messageFragmentTimeout) {
        this.messageFragmentTimeout = messageFragmentTimeout;
    }

    public void setCotFieldLength(int cotFieldLength) {
        this.cotFieldLength = cotFieldLength;
    }

    public void setCommonAddressFieldLength(int commonAddressFieldLength) {
        this.commonAddressFieldLength = commonAddressFieldLength;
    }

    public void setIoaFieldLength(int ioaFieldLength) {
        this.ioaFieldLength = ioaFieldLength;
    }

    public void setMaxTimeNoAckReceived(int maxTimeNoAckReceived) {
        this.maxTimeNoAckReceived = maxTimeNoAckReceived;
    }

    public void setMaxTimeNoAckSent(int maxTimeNoAckSent) {
        this.maxTimeNoAckSent = maxTimeNoAckSent;
    }

    public void setMaxIdleTime(int maxIdleTime) {
        this.maxIdleTime = maxIdleTime;
    }

    public void setMaxUnconfirmedIPdusReceived(int maxUnconfirmedIPdusReceived) {
        this.maxUnconfirmedIPdusReceived = maxUnconfirmedIPdusReceived;
    }

    public void setMaxNumOfOutstandingIPdus(int maxNumOfOutstandingIPdus) {
        this.maxNumOfOutstandingIPdus = maxNumOfOutstandingIPdus;
    }

    public void setConnectionTimeout(int time) {
        this.connectionTimeout = time;

    }

}
