package Server;

import pojo.Check;
import utils.CheckUtils;
import utils.LoginUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.List;

/**
 * 该类为多线程类，用于服务端
 */
public class ServerThread implements Runnable {

    private Socket client = null;

    public ServerThread(Socket client) {
        this.client = client;
    }


    @Override
    public void run() {
        /*
        * 循环接收客户端的请求命令
        * */
        while (true) {
            //接受客户端的请求命令code
            String code;
            String username = null, check_in_time, check_place, check_out_time;
            BufferedReader buf;
            try {
                buf = new BufferedReader(new InputStreamReader(client.getInputStream()));
                code = buf.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (code.equals("UserLogin")) {
                try {
                    username = buf.readLine();
                    String password = buf.readLine();
                    boolean flag = LoginUtil.userlogin(username, password);
                    PrintStream out = new PrintStream(client.getOutputStream());
                    if (flag) {
                        System.out.println("用户登陆成功");
                        out.println("1");
                    } else {
                        out.println("0");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if (code.equals("UserCheckIn")) {
                try {
                    System.out.println("用户签到");
                    username = buf.readLine();
                    check_place = buf.readLine();
                    check_in_time = buf.readLine();

                    Check check = new Check(0, username, check_in_time, null, check_place, "0");

                    CheckUtils.CheckIn(check);
                    System.out.println("用户签到成功");


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if (code.equals("UserCheckOut")) {
                try {
                    System.out.println("用户签退");
                    username = buf.readLine();
                    check_out_time = buf.readLine();
                    CheckUtils.checkOut(username, check_out_time);
                    System.out.println("用户签退成功");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if (code.equals("UserCheckSelf")) {
                
                System.out.println("用户查看自己的签到记录");
                try {
                    username = buf.readLine();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                PrintStream out = null;
                try {
                    out = new PrintStream(client.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                List<Check> checks = CheckUtils.selectByName(username);
                for (Check check : checks) {
                    out.println(check);
                }
                out.println("over");


            }
            if (code.equals("AdminLogin")) {
                try {
                    String adminname = buf.readLine();
                    String password = buf.readLine();
                    boolean flag = LoginUtil.adminlogin(adminname, password);
                    PrintStream out = new PrintStream(client.getOutputStream());
                    if (flag) {
                        System.out.println("管理员陆成功");
                        out.println("1");
                    } else {
                        out.println("0");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if (code.equals("AdminAllCheck")) {
                try {
                    System.out.println("查看全部记录");
                    PrintStream out = new PrintStream(client.getOutputStream());
                    List<Check> checks = CheckUtils.selectAll();
                    for (Check check : checks) {
                        out.println(check);
                    }
                    out.println("over");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (code.equals("AdminAlready")) {
                try {
                    System.out.println("查看已签到名单");
                    PrintStream out = new PrintStream(client.getOutputStream());
                    List<Check> checks = CheckUtils.selectAlready();
                    for (Check check : checks) {
                        out.println(check);
                    }
                    out.println("over");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (code.equals("AdminNotYet")) {
                try {
                    System.out.println("查看未签到名单");
                    PrintStream out = new PrintStream(client.getOutputStream());

                    List<String> names = CheckUtils.selectNotYet();
                    for (String name : names) {
                        out.println(name);
                    }
                    out.println("over");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }

    }
}