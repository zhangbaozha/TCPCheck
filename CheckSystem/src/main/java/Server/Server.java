package Server;

import java.net.ServerSocket;
import java.net.Socket;


public class Server {
    public static void main(String[] args) throws Exception{
        //服务端在8085端口监听客户端请求的TCP连接
        ServerSocket server = new ServerSocket(8085);
        Socket client = null;
        boolean f = true;
        while(f){
            client = server.accept();
            System.out.println("与客户端连接成功！");
            //为每个客户端连接开启一个线程
            new Thread(new ServerThread(client)).start();
        }
        server.close();
    }
}
