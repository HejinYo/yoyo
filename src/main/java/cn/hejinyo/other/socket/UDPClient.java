package cn.hejinyo.other.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/*
 * 客户端
 */
public class UDPClient {
    public static void main(String[] args) throws IOException {
        /**
         * 向服务器端发送数据
         */
        while (true) {
            Scanner s = new Scanner(System.in);
            String str = null;
            System.out.println("请输入发送的字符串：");
            str = s.next();

            // 1.定义服务器的地址、端口号、数据
            InetAddress address = InetAddress.getByName("52.196.30.1");
            int port = 8800;
            byte[] data = str.getBytes();
            // 2.创建数据报，包含发送的数据信息
            DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
            // 3.创建DatagramSocket对象
            DatagramSocket socket = new DatagramSocket();
            // 4.向服务器端发送数据报
            socket.send(packet);

            /**
             * 接收服务器端响应的数据
             */
            // 1.创建数据报，用于接收服务器端响应的数据
            byte[] data2 = new byte[1024];
            DatagramPacket packet2 = new DatagramPacket(data2, data2.length);
            // 2.接收服务器响应的数据
            socket.receive(packet2);
            // 3.读取数据
            String reply = new String(data2, 0, packet2.getLength());
            System.out.println("服务器相应：" + reply);
            // 4.关闭资源
            socket.close();
        }

    }
}