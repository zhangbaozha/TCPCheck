package tech.codinglink;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerFrame extends JFrame implements ActionListener,Runnable {
    private JButton send;//发送按钮
    private JTextField showIP,showPort;//显示ip和端口号
    private JTextArea showChat,sendChat;//显示聊天窗口和发送窗口
    private String portIn;
    private String OutStr=null;
    private ServerSocket server;
    private Socket socket;//创建一个套接字
    private Thread thread;
    private BufferedReader input;
    private BufferedWriter output;


    public ServerFrame(){
        this.setDefaultLookAndFeelDecorated(true);

        //创建及设置窗口
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setTitle("服务器端");
        send=new JButton("发送");
        showPort=new JTextField(12);


        Box v1=Box.createVerticalBox();
        v1.add(new Label("Port:"));


        Box v2=Box.createVerticalBox();
        v2.add(showPort);


        Box base=Box.createHorizontalBox();
        base.add(v1);
        base.add(v2);


        Container con=this.getContentPane();
        con.setLayout(new FlowLayout());
        showChat=new JTextArea(16,25);
        sendChat=new JTextArea(4,18);
        con.add(base);
        con.add(new JScrollPane(showChat));
        con.add(new JScrollPane(sendChat));
        con.add(send);
        send.addActionListener(this);
        send.setActionCommand("send");



        this.setBounds(700,200,300,500);//设置窗体大校
        portIn=JOptionPane.showInputDialog(null,"请输入端口号：","8080");
        showPort.setText(portIn);
        this.setVisible(true);
        this.setResizable(false);
        this.setVisible(true);
        send.setEnabled(false);
        try{
            server = new ServerSocket(Integer.parseInt(portIn));
            showChat.setText("服务器正在监听...");
            socket = server.accept();
            showChat.setText("已有客户端连接上，现在可以开始聊天!\n");
            sendChat.setEditable(true);
            send.setEnabled(true);
            //使用BufferedReader和BufferWriter包装输入输出流
            input= new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            thread=new Thread(this);
            thread.setPriority(Thread.MIN_PRIORITY);
            thread.start();
        }catch(IOException c){
            showChat.append("对不起，不能创建服务器！");
            sendChat.setEditable(false);
            send.setEnabled(false);
        }catch(NumberFormatException c){
            sendChat.setText("端口号请输入数字！");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        OutStr = sendChat.getText();
        if (OutStr.length() > 0) {
            try {
                output.write(OutStr + "\n");
                output.flush();
                showChat.append("我说: " + OutStr + "\n");
                sendChat.setText(null);
            } catch (IOException es) {
                showChat.append("消息发送失败!\n");
            }
        } else {
            JOptionPane.showMessageDialog(this, "信息不能为空!", "警告提示!", JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public void run() {
        try{
            while(true){
                showChat.append("对方说:"+input.readLine()+"\n");
                Thread.sleep(1000);
            }
        }catch(IOException el){
            showChat.setText("客户离开");
        }catch (InterruptedException e) { }
    }
}
