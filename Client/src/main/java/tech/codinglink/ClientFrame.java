package tech.codinglink;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

public class ClientFrame extends JFrame implements ActionListener,Runnable {
    private JButton connect, send;//连接和发送按钮
    private JTextField showIP, showPort;//显示ip和端口号
    private JTextArea showChat, sendChat;//显示聊天窗口和发送窗口
    private String portIn, ipIn;
    private String OutStr = null;
    private Socket socket;//创建一个套接字
    private BufferedReader input;
    private BufferedWriter output;
    private Thread thread = null;

    public ClientFrame() {
        this.setDefaultLookAndFeelDecorated(true);

        //关闭窗口即关闭进程
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setTitle("客户端");
        connect = new JButton("连接");
        send = new JButton("发送");
        showPort = new JTextField(12);//端口填写框
        showIP = new JTextField(12);//ip地址填写框


        Box v1 = Box.createVerticalBox();
        v1.add(new Label("ip:"));
        v1.add(new Label("Port:"));


        Box v2 = Box.createVerticalBox();
        v2.add(showIP);
        v2.add(showPort);


        Box base = Box.createHorizontalBox();
        base.add(v1);
        base.add(v2);


        Container con = this.getContentPane();
        con.setLayout(new FlowLayout());
        showChat = new JTextArea(16, 25);
        sendChat = new JTextArea(4, 18);
        con.add(base);
        con.add(new JScrollPane(showChat));
        con.add(new JScrollPane(sendChat));
        con.add(connect);
        con.add(send);
        //设置监听事件
        connect.addActionListener(this);
        connect.setActionCommand("connect");
        send.addActionListener(this);
        send.setActionCommand("send");
        thread = new Thread();

        //设置窗体大小
        this.setBounds(700, 200, 300, 500);
        portIn = JOptionPane.showInputDialog(null, "请输入端口号：", "8080");
        ipIn = JOptionPane.showInputDialog(null, "请输入ip地址", "127.0.0.1");
        showPort.setText(portIn);
        showIP.setText(ipIn);
        this.setVisible(true);
        this.setResizable(false);
        this.setVisible(true);

        send.setEnabled(false);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("connect")) {
            try {
                sendChat.setEnabled(true);
                send.setEnabled(true);
                //将套接字实例化
                socket = new Socket(ipIn, Integer.parseInt(portIn));

                //提示用户服务器连接成功
                showChat.append("服务器连接成功，可以开始聊天\n");

                //建立连接
                input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

                thread = new Thread(this);
                thread.setPriority(Thread.MIN_PRIORITY);
                thread.start();
            } catch (IOException ioException) {
                showChat.setText("对不起，连接服务器失败！");
                sendChat.setEditable(false);
                send.setEnabled(false);
            } catch (NumberFormatException c) {
                sendChat.setText("端口号请输入数字！");
            }
        } else if (e.getActionCommand().equals("send")) {
            OutStr = sendChat.getText();
            if (OutStr.length() > 0) {
                try {
                    output.write(OutStr+"\n");
                    output.flush();
                    showChat.append("我说: " + OutStr + "\n");
                    sendChat.setText(null);
                } catch (IOException es) {
                    showChat.append("信息发送失败!\n");
                }
            } else {
                JOptionPane.showMessageDialog(this, "信息不能为空!", "警告提示!", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                showChat.append("对方说:" + input.readLine() + "\n");
                Thread.sleep(1000);
            }
        } catch (IOException el) {
            showChat.setText("与服务器已断开连接");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
