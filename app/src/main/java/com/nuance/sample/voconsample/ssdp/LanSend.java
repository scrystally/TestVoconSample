package com.nuance.sample.voconsample.ssdp;

import android.content.Context;
import android.util.Log;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class LanSend {

    /**
     * @param args
     * @throws Exception
     */

    // 广播地址
    private static final String BROADCAST_IP = SSDPUtils.ADDRESS;// 广播IP
    private static final int BROADCAST_INT_PORT = SSDPUtils.PORT; // 不同的port对应不同的socket发送端和接收端

    MulticastSocket broadSocket;// 用于接收广播信息
    public static boolean work = false;// 循环监听因子
    InetAddress broadAddress;// 广播地址
    // DatagramSocket数据报Socket
    DatagramSocket sender;// 数据流套接字 相当于码头，用于发送信息
    private Context context;

    // private InetSocketAddress mMulticastGroup;
    public LanSend(Context contex) {
        try {
            this.context = contex;
            // 初始化
            broadSocket = new MulticastSocket(BROADCAST_INT_PORT); // 多播Socket
            // mMulticastGroup = new InetSocketAddress(SSDPUtils.ADDRESS,
            // SSDPUtils.PORT);
            broadAddress = InetAddress.getByName(BROADCAST_IP);// 获取网络多播地址
            sender = new DatagramSocket();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("*****lanSend初始化失败*****" + e.toString());
        }
    }

    public void join() {
        try {
            Log.e("LanSend", "join");
            broadSocket.joinGroup(broadAddress); // 把套接字socket加入到组播地址，这样就能接收到组播信息
            new Thread(new GetPacket()).start(); // 新建一个线程，用于循环侦听端口信息
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("*****加入组播失败*****");
        }

    }

    // 广播发送查找在线用户
    void sendGetUserMsg() {
        byte[] b = new byte[1024];
        DatagramPacket packet; // 数据包，相当于集装箱，封装信息
        try {
            b = SSDPUtils.buildSSDPSearchString().getBytes();
            packet = new DatagramPacket(b, b.length, broadAddress,
                    BROADCAST_INT_PORT); // 广播信息到指定端口
            sender.send(packet);// 单播信息会给目标主机
            System.out.println("*****已发送请求*****");
        } catch (Exception e) {
            System.out.println("*****查找出错*****");
        }
    }

    // 当局域网内的在线机子收到广播信息时响应并向发送广播的ip地址(此处广播自己的存在来响应)主机发送返还信息，达到交换信息的目的
    void returnUserMsg(String mac) {
        byte[] b = new byte[1024];
        DatagramPacket packet;
        try {
            b = SSDPUtils.buildSSDPAliveString(mac).getBytes();
            packet = new DatagramPacket(b, b.length, broadAddress,
                    BROADCAST_INT_PORT);
            sender.send(packet);
            System.out.print("发送信息成功！");
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("*****发送返还信息失败*****" + e);
        }
    }

    // 当局域网内的在线机子收到广播信息时响应并向发送广播的ip地址主机发送返还信息，达到交换信息的目的
    void returnUserMsg(String mac, InetAddress inetaddress, int port) {
        byte[] b = new byte[1024];
        DatagramPacket packet;
        try {
            b = SSDPUtils.buildSSDPAliveString(mac).getBytes();
            packet = new DatagramPacket(b, b.length, inetaddress, port);
            sender.send(packet);
            System.out.print("发送信息成功！");
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("*****发送返还信息失败*****" + e);
        }
    }

    // 当局域网某机子下线是需要广播发送下线通知
    void offLine() {
        byte[] b = new byte[1024];
        DatagramPacket packet;
        try {
            b = SSDPUtils.buildSSDPByebyeString().getBytes();
            packet = new DatagramPacket(b, b.length, broadAddress,
                    BROADCAST_INT_PORT);
            sender.send(packet);
            System.out.println("*****已离线*****");
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("*****离线异常*****");
        }
    }

    class GetPacket implements Runnable { // 新建的线程，用于侦听，packet数据包
        public void run() {
            DatagramPacket inPacket;
            work = true;
            String[] message;

            while (work) {
                try {
                    Log.e("LanSend", "GetPacket while");
                    inPacket = new DatagramPacket(new byte[1024], 1024);
                    broadSocket.receive(inPacket); // 接收广播信息并将信息封装到inPacket中
                    // inPacket.getData()
                    // message=new
                    // String(inPacket.getData(),0,inPacket.getLength()).split("@");
                    // //获取信息，并切割头部，判断是何种信息（find--上线，retn--回答，offl--下线）
                    String data = new String(inPacket.getData()).trim();

                    if (data != null) {
                        String socketAddress = inPacket.getSocketAddress()
                                .toString();
                        String[] datas = data.split(SSDPUtils.NEWLINE);
                        for (int i = 0; i < datas.length; i++) {
                            Log.e("LanSend", "datas[" + i + "] =  " + datas[i]);
                            if (datas[i].trim()
                                    .equalsIgnoreCase("ST: ssdp:all")) {
                                Log.e("ALIVE", "response");
                                String mac = SSDPUtils.getUserId(context);
                                InetAddress sourceip = inPacket.getAddress();
                                int port = inPacket.getPort();
                                returnUserMsg(mac, sourceip, port);
                                break;
                            }

                        }
                        Log.e("LanSend", "收到的数据包的 data ：" + data
                                + "    数据包的socketAddress：" + socketAddress);
                        System.out.println("收到的数据包的 data ：" + data
                                + "    数据包的socketAddress：" + socketAddress);
                    }

                    // if(message[1].equals(lanDemo.ip))continue; //忽略自身
                    // if(message[0].equals("find")){ //如果是请求信息
                    // System.out.println("新上线主机："+" ip："+message[1]+" 主机："+message[2]);
                    // returnUserMsg(message[1]);
                    // }
                    // else if(message[0].equals("retn")){ //如果是返回信息
                    // System.out.println("找到新主机："+" ip："+message[1]+" 主机："+message[2]);
                    // }
                    // else if(message[0].equals("offl")){ //如果是离线信息
                    // System.out.println("主机下线："+" ip："+message[1]+" 主机："+message[2]);
                    // }

                } catch (Exception e) {
                    // TODO: handle exception
                    System.out.println("线程出错 " + e);
                }
            }
        }
    }
}