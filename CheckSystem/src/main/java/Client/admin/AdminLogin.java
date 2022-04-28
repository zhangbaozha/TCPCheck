package Client.admin;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;


public class AdminLogin{

    //在类中定义初始化界面的方法
    public AdminLogin(Socket socket,String adminname,String password) {
        PrintStream out = null;
        BufferedReader input;
        try {
            out = new PrintStream(socket.getOutputStream());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
        try {
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }


        out.println("AdminLogin");
        out.println(adminname);
        out.println(password);
        try {
            String code = input.readLine();
            if(code.equals("1")){
                System.out.println("成功");
                JOptionPane.showMessageDialog(null, "登录成功");
            }
            else{
                System.out.println("登录失败");
                JOptionPane.showMessageDialog(null, "登录失败");
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }


}









