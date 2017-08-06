package cn.hejinyo.other.socket;

import cn.hejinyo.other.model.Jzt_Gps;
import net.sf.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * socket 线程类
 *
 * @author zhangzhongwen
 */
public class SocketThread extends Thread {

    public void run() {
        /*
                 * 接收客户端发送的数据
                 */
        System.out.println("======服务器端已经启动，等待客户端发送数据======");
        while (true) {
            try {
                // 1.创建服务器端DatagramSocket，指定端口
                DatagramSocket socket = new DatagramSocket(8800);
                // 2.创建数据报，用于接收客户端发送的数据
                byte[] data = new byte[1024];// 创建字节数组，指定接收的数据包的大小
                DatagramPacket packet = new DatagramPacket(data, data.length);
                // 3.接收客户端发送的数据
                socket.receive(packet);// 此方法在接收到数据报之前会一直阻塞
                // 4.读取数据
                String info = new String(data, 0, packet.getLength());
                System.out.println("接受到客户端消息：“" + info + "”");

                /**
                 * 分析数据，写入数据库
                 */
                JSONObject jsonObject = JSONObject.fromObject(info);

                String devid = jsonObject.getString("devid");
                Double longitude = jsonObject.getDouble("longitude");
                Double latitude = jsonObject.getDouble("latitude");
                Jzt_Gps jzt_gps = new Jzt_Gps();
                jzt_gps.setDevid(devid);
                jzt_gps.setLongitude(longitude);
                jzt_gps.setLatitude(latitude);
                JDBC_zt_Gps jdbcZtGps = new JDBC_zt_Gps();
                int count = jdbcZtGps.addJzt_Gps(jzt_gps);
                String result = "";
                if(count == 1){
                    result = "增加成功！";
                }else{
                    result = "操作失败！";
                }
                /**
                 * 向客户端响应数据
                 */
                // 1.定义客户端的地址、端口号、数据
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                byte[] data2 = (result).getBytes();
                // 2.创建数据报，包含响应的数据信息
                DatagramPacket packet2 = new DatagramPacket(data2, data2.length, address, port);
                // 3.响应客户端
                socket.send(packet2);
                // 4.关闭资源
                socket.close();
            } catch (SocketException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}