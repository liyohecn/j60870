import cn.liyohe.j60870.MasterStation;

import java.io.IOException;

public class MasterStationTester {

    public static void main(String[] args) throws IOException, InterruptedException {
        MasterStation station = MasterStation.builder()
                .withAddress("192.168.65.206")
                .build();
        try {
            station.connect();
        }catch (Exception e){
            station.close();
            e.printStackTrace();
        }
    }
}
