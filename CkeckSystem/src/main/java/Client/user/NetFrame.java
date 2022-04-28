package Client.user;


import utils.NetUtils;

import javax.swing.*;
import java.awt.*;
import java.net.UnknownHostException;

/**
 * @Description:网络参数界面
 * @Author: zhw
 * @Date: 2022/4/28
 */
public class NetFrame extends JFrame {
    public NetFrame() throws UnknownHostException {
        super();
        setTitle("网络参数");
        setSize(400, 126);
        setLocationRelativeTo(null);
        String[] columnNames = {"", ""};
        NetUtils N1 = new NetUtils();
        String[][] context ={
                {"计算机名称",""},
                {"IP地址",""},
                {"子网掩码",""},
                {"DNS设置","221.131.143.69"},
                {"网卡地址",""}
        };
        context[0][1] = N1.getHostName();
        context[1][1] = N1.getIP();
        context[2][1] = N1.getSubNetMask(N1.getIP());
        context[4][1] = N1.getGateWay();
        JTable table = new JTable(context, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        setVisible(true);
    }

}
