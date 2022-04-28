package Client.user;



import com.alibaba.fastjson.JSON;
import pojo.Check;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * @Description:用户主界面
 * @Author: zhw
 * @Date: 2022/4/28
 */
public class UserFrame implements ActionListener {
    /** 用于建立与服务器的连接 */
   public Socket socket;
    /** 用于存储用户名，以便签到、签退功能的实现*/
   public String userName;
    /** 用于判断用户是否登录 */
   public boolean hasLogin;
    /** 用于判断用户是否签到 */
   public boolean hasCheckIn;



   public UserFrame(){
       JFrame jf = new JFrame("考勤系统--普通用户");
       jf.setVisible(true);
       jf.setResizable(false);//固定窗口大小
       jf.setBounds(100,100,500,350);
       //窗口可关闭
       jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       //窗口位于屏幕中间
       jf.setLocationRelativeTo(null);

       //创建面板
       JPanel jPanel = new JPanel();
       jPanel.setBounds(0,0,484,311);
       //窗口添加面板
       jf.getContentPane().add(jPanel);
       jPanel.setLayout(null);


       //欢迎标签
       JLabel welJla = new JLabel("欢迎进入用户界面！");
       welJla.setFocusable(false);
       welJla.setBounds(99,20,356,30);
       welJla.setForeground(Color.BLUE);
       welJla.setFont(new Font("宋体", Font.PLAIN,30));
       jPanel.add(welJla);

       //登录按钮
       JButton btn1 = new JButton("登录");
       btn1.setActionCommand("UserLogin");
       btn1.addActionListener(this);
       btn1.setFocusPainted(false);
       btn1.setFont(new Font("宋体",Font.PLAIN,20));
       btn1.setBounds(145,73,193,30);
       jPanel.add(btn1);

       //签到按钮
       JButton btn2 = new JButton("签到");
       btn2.setActionCommand("CheckIn");
       btn2.addActionListener(this);
       btn2.setFocusPainted(false);
       btn2.setFont(new Font("宋体",Font.PLAIN,20));
       btn2.setBounds(145,113,193,30);
       jPanel.add(btn2);

       //登退按钮
       JButton btn3 = new JButton("签退");
       btn3.setActionCommand("CheckOut");
       btn3.addActionListener(this);
       btn3.setFocusPainted(false);
       btn3.setFont(new Font("宋体",Font.PLAIN,20));
       btn3.setBounds(145,153,193,30);
       jPanel.add(btn3);

       //查看自己的签到记录按钮
       JButton btn4 = new JButton("查看自己的签到记录");
       btn4.setActionCommand("CheckMyCheck");
       btn4.addActionListener(this);
       btn4.setFocusPainted(false);
       btn4.setFont(new Font("宋体",Font.PLAIN,20));
       btn4.setBounds(145,193,193,30);
       jPanel.add(btn4);

       //下拉菜单
       JMenuBar menuBar1 = new JMenuBar();
       JMenu menu1 = new JMenu("菜单");

       //TCP连接菜单条
       JMenuItem subMenu1 = new JMenuItem("TCP连接");
       subMenu1.setActionCommand("Connect");
       subMenu1.addActionListener(this);

       //获取网络参数菜单条
       JMenuItem subMenu2 = new JMenuItem("获取网络参数");
       subMenu2.setActionCommand("Net");
       subMenu2.addActionListener(this);

       //退出菜单条
       JMenuItem subMenu4 = new JMenuItem("退出");
       subMenu4.setActionCommand("Exit");
       subMenu4.addActionListener(this);

       //将菜单条添加到菜单中
       menu1.add(subMenu1);
       menu1.add(subMenu2);
       menu1.add(subMenu4);
       menuBar1.add(menu1);

        //将菜单加入窗口
       jf.add(menuBar1,BorderLayout.NORTH);
       menuBar1.setVisible(true);
       //窗口可见
       jf.setVisible(true);
   }

/**
 * @Description:重写ActionListener的actionPerformed（）方法，是功能核心区
 * @Author: zhw
 * @Date: 2022/4/28
 */
    @Override
    public void actionPerformed(ActionEvent e) {
        //连接服务器
        if (e.getActionCommand().equals("Connect")) {
            String portIn = JOptionPane.showInputDialog(null, "请输入端口号：", "8085");
            String ipIn = JOptionPane.showInputDialog(null, "请输入ip地址", "127.0.0.1");
            try {
                socket = new Socket(ipIn, Integer.parseInt(portIn));
                JOptionPane.showMessageDialog(null, "连接成功！");

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "连接失败！");
                throw new RuntimeException(ex);
            }
        }


        //获取网络参数
        if (e.getActionCommand().equals("Net")) {

            try {
                new NetFrame();
            } catch (UnknownHostException ex) {
                throw new RuntimeException(ex);
            }

        }


        //退出程序
        if (e.getActionCommand().equals("Exit")) {
            System.exit(0);
        }


        //用户登录
        if (e.getActionCommand().equals("UserLogin")) {
            if (socket == null) {
                JOptionPane.showMessageDialog(null, "请先连接服务器");
            } else {
                //设置窗体对象的属性值
                JPasswordField text_password;
                JTextField text_username;
                JFrame frame = new JFrame();
                frame.setTitle("普通用户登录");
                frame.setSize(400, 250);
                frame.setLocationRelativeTo(null);
                frame.setResizable(false);
                frame.setFont(new Font("宋体", Font.PLAIN, 14));
                FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 10, 10);
                frame.setLayout(fl);
                JLabel labname = new JLabel("账号：");
                labname.setFont(new Font("宋体", Font.PLAIN, 14));
                frame.add(labname);
                text_username = new JTextField();
                Dimension dim1 = new Dimension(300, 30);
                text_username.setPreferredSize(dim1);
                frame.add(text_username);
                JLabel labpass = new JLabel("密码：");
                labpass.setFont(new Font("宋体", Font.PLAIN, 14));
                frame.add(labpass);
                text_password = new JPasswordField();
                text_password.setPreferredSize(dim1);
                frame.add(text_password);
                JButton button = new JButton();
                Dimension dim2 = new Dimension(100, 30);
                button.setText("登录");
                button.setActionCommand("Login");
                button.addActionListener(this);
                button.setFont(new Font("宋体", Font.PLAIN, 14));
                button.setSize(dim2);
                frame.add(button);
                frame.setVisible(true);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String username = text_username.getText();
                        String password = text_password.getText();
                        userName = username;
                        new UserLogin(socket,username,password);
                        hasLogin = true;
                    }
                });

            }

        }


        //用户签到
        if (e.getActionCommand().equals("CheckIn")) {
           if (hasLogin==false) {
               JOptionPane.showMessageDialog(null, "请先登录");
           } else {
                //签到窗口
                JFrame frame = new JFrame();
                frame.setTitle("普通用户签到");
                frame.setSize(400, 250);
                frame.setLocationRelativeTo(null);
                frame.setResizable(false);
                frame.setFont(new Font("宋体", Font.PLAIN, 14));
                FlowLayout fl = new FlowLayout(FlowLayout.CENTER, 10, 10);
                frame.setLayout(fl);
                JLabel labname = new JLabel("签到地址：");
                labname.setFont(new Font("宋体", Font.PLAIN, 14));
                frame.add(labname);
                JTextField text_place = new JTextField();
                Dimension dim1 = new Dimension(300, 30);
                text_place.setPreferredSize(dim1);
                frame.add(text_place);
                JButton button = new JButton();
                Dimension dim2 = new Dimension(100, 30);
                button.setText("确认签到");
                button.setFont(new Font("宋体", Font.PLAIN, 14));
                button.setSize(dim2);
                frame.add(button);
                frame.setVisible(true);
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String place = text_place.getText();
                        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = new Date(System.currentTimeMillis());
                        String check_in_time;
                        check_in_time = formatter.format(date);
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

                        out.println("UserCheckIn");
                        out.println(userName);
                        out.println(place);
                        out.println(check_in_time);
                        JOptionPane.showMessageDialog(null, "签到成功");
                        hasCheckIn = true;
                        frame.setVisible(false);

                    }
                });

           }

        }

        //签退
        if (e.getActionCommand().equals("CheckOut")) {
                SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(System.currentTimeMillis());
                String check_out_time;
                check_out_time = formatter.format(date);
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
                out.println("UserCheckOut");
                out.println(userName);
                out.println(check_out_time);
                JOptionPane.showMessageDialog(null, "签退成功");
        }

        //用户查看自己的签到记录
        if (e.getActionCommand().equals("CheckMyCheck")) {
            if (hasLogin==false) {
                JOptionPane.showMessageDialog(null, "请先登录");
            }else {
                PrintStream out = null;
                BufferedReader input;
                try {
                    out = new PrintStream(socket.getOutputStream());
                    input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                out.println("UserCheckSelf");
                out.println(userName);
                List<Check> checks = new ArrayList<Check>();
                boolean flag = true;
                while (flag) {
                    String str = null;
                    try {
                        str = input.readLine();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    if (str.equals("over")) {
                        flag = false;
                     } else {
                        Check check = JSON.parseObject(str, Check.class);
                        checks.add(check);
                    }
                }
                DefaultTableModel model = null;
                JTable table = null;
                JFrame f = new JFrame("\"TableDemo\"");
                String[][] datas = {};
                String[] titles = {"姓名", "签到时间", "签退时间", "签到地点"};
                model = new DefaultTableModel(datas, titles);
                table = new JTable(model);
                for (Check check : checks) {
                    model.addRow(new String[]{check.getUsername(), check.getCheck_in_time(), check.getCheck_out_time(), check.getCheck_place()});
                }
                f.add(new JScrollPane(table));
                f.setSize(400, 300);
                f.setLocationRelativeTo(null);
                f.setVisible(true);
            }
        }

    }
}
