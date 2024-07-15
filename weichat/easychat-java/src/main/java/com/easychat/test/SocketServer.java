package com.easychat.test;

import java.io.*;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Scanner;
import java.net.Socket;
public class SocketServer {
    public static void main(String[] args) {
        ServerSocket server = null;
        try{
            server = new ServerSocket(1024);
            System.out.println("服务已经启动");
            HashMap<String,Socket> clientMap = new HashMap<>();
            while(true){
                Socket socket = server.accept();
                String ip = socket.getInetAddress().getHostAddress();
                System.out.println("有客户端连接ip:"+ip+"端口:"+socket.getPort());

                String clientKey = ip + socket.getPort();
                clientMap.put(clientKey,socket);
                new Thread(()->{
                    while(true){
                        try {
                            InputStream inputStream = null;
                            inputStream = socket.getInputStream();
                            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"GBK");
                            BufferedReader bufferedReader= new BufferedReader(inputStreamReader);
                            String readData = bufferedReader.readLine();
                            System.out.println("收到客户端消息:"+readData);
                            clientMap.forEach((k,v)->{
                                try{
                                    OutputStream outputStream = v.getOutputStream();
                                    //PrintWriter printWriter = new PrintWriter(outputStream);
                                    PrintWriter printWriter1 = new PrintWriter(new OutputStreamWriter(outputStream,"GBK"));
                                    printWriter1.println(v.getPort()+":"+readData);
                                    printWriter1.flush();
                                }catch(Exception e){
                                    e.printStackTrace();
                                }

                            });
//
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
