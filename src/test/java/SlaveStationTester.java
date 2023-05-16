import cn.liyohe.j60870.SlaveStation;

import java.io.IOException;

public class SlaveStationTester {

    public static void main(String[] args) throws IOException, InterruptedException {
        SlaveStation slaveStation = SlaveStation.builder()
                .withMaxConnections(1)
                .withMaxTimeNoAckSent(1000)
                .build();
        try {
            slaveStation.start();
        } catch (Exception e) {
            slaveStation.stop();
            e.printStackTrace();
        }

        System.out.println(slaveStation);
    }
}
