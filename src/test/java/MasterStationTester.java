import cn.liyohe.j60870.MasterStation;

import java.io.IOException;

public class MasterStationTester {

    public static void main(String[] args) throws IOException, InterruptedException {
        MasterStation station = MasterStation.builder()
                .withMaxIdleTime(1000)
                .withAddress("192.168.65.206")
                .build();
        try {
            station.connect();
            System.out.println("主站启动成功");
        }catch (Exception e){
            station.close();
            e.printStackTrace();
        }

    }
}
