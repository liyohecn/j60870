package cn.liyohe.j60870;


import cn.liyohe.j60870.ie.IeChecksum;
import cn.liyohe.j60870.ie.InformationObject;

/**
 * apdu建设者
 *
 * @author liyohe
 * @date 2023/05/16
 */
public class APduBuilder {

    public static APdu buildSFormatPdu(int receiveSequenceNumber) {
        return new APdu(0, receiveSequenceNumber, APdu.ApciType.S_FORMAT, null);
    }

    public static ASdu buildAllCell() {
        return new ASdu(ASduType.C_IC_NA_1, false, CauseOfTransmission.ACTIVATION,
                false, false, 0, 1,
                new InformationObject(0, new IeChecksum(20)));
    }
}
