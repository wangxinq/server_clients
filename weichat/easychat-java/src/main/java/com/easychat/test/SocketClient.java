package com.easychat.test;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class SocketClient {
    public static void main(String[] args) {
        Socket socket = null;
        try{
            socket = new Socket("127.0.0.1",1024);
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);
            Scanner scanner = new Scanner(System.in);
            Socket finalSocket = socket;
            new Thread(()->{
                while(true){
                    try{
                        System.out.println("请输入内容");
                        String input = scanner.nextLine();
                        printWriter.println(input);
                        printWriter.flush();

                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }).start();
            InputStream inputStream = socket.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream,"GBK");
            BufferedReader bufferedReader= new BufferedReader(inputStreamReader);
            new Thread(()->{
                while(true){
                    try{
                        String sd = bufferedReader.readLine();
                        System.out.println("收到服务端消息:"+sd);
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }

                }
            }).start();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
