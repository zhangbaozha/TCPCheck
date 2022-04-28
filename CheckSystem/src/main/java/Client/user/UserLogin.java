package Client.user;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
/**
 * @Description:用户登录的实现类
 * @Author: zhw
 * @Date: 2022/4/28
 */

public class UserLogin{
    /**
     *
     * @param socket: 与服务器的TCP连接
     * @param username：用户名
     * @param password：密码
     */
    public UserLogin(Socket socket,String username,String password) {
        /*
        * 创建Socket的读写
        * */
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

        out.println("UserLogin");
        out.println(username);
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









