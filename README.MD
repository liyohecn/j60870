# IEC 60870-5-104

## 概述
该项目为 openmuc IEC104 协议的 Netty 实现版。

官方仓库 [openmuc](https://github.com/gythialy/j60870/).

官方地址 [openmuc](https://www.openmuc.org/iec-60870-5-104/).

## 依赖
数据解析依赖于 openmuc 的 j60870 实现。

网络连接依赖于 Netty 4.1.x。


```shell

```xml

## 用法

```java
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
```