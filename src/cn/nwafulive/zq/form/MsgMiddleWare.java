package cn.nwafulive.zq.form;

import cn.nwafulive.zq.MsgReceiver;
import cn.nwafulive.zq.MsgSender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 * @Author ZhangQiong nwsuafzq@hotmail.com
 * @Date 2017/4/7
 * @Time 20:41.
 */
public class MsgMiddleWare {

    private JPanel panel1;
    private JTextField sendArea;
    private JPanel sendPanel;
    private JLabel receiveLabel;
    private JLabel sendLabel;
    private JTextArea receiveArea;
    private JButton sendButton;
    private JPanel rPanel;
    private JRadioButton q1RadioButton;
    private JRadioButton q2RadioButton;
    public JScrollPane scrollPane;
    private JLabel alertLabel;
    private JPanel sPanel;
    private JTabbedPane tabbedPane1;

//    private static String url = "tcp://localhost:61616";
    private static String url = "tcp://172.29.23.100:61616";

    private static String user = null;
    private static String password = null;

    private static String send_queue;
    private static String receive_queue;

    private static String sendAreaText = null;

    public MsgMiddleWare() {

        sendButton.addActionListener(new ActionListener() {     /* 发送按钮事件 */
            @Override
            public void actionPerformed(ActionEvent e) {
                sendAreaText = sendArea.getText();
                //System.out.println(sendAreaText);
                if (q1RadioButton.isSelected() || q2RadioButton.isSelected()) {
                    alertLabel.setText("");
                    new Thread(new MsgSender(send_queue, url, user, password, sendAreaText), "Name-Sender").start();
                    System.out.println(send_queue);
                    sendArea.setText("");
                } else {
                    alertLabel.setText("必须选择一个队列!");
                }
//                alertLabel.setText("");
            }
        });
        sendButton.setMnemonic(KeyEvent.VK_ENTER); //发送按钮绑定快捷键 回车

        q1RadioButton.addActionListener(new ActionListener() {  /* radiobutton事件 */
            @Override
            public void actionPerformed(ActionEvent e) {
                send_queue = "Q1";
                receive_queue = "Q2";
                new Thread(new MsgReceiver(receive_queue, url, user, password, receiveArea), "Name-Receiver").start();
                System.out.println(send_queue);

            }
        });
        q2RadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                send_queue = "Q2";
                receive_queue = "Q1";
                new Thread(new MsgReceiver(receive_queue, url, user, password, receiveArea), "Name-Receiver").start();
                System.out.println(send_queue);
            }
        });

        sendArea.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == sendArea) {
                    sendAreaText = sendArea.getText();
                    //System.out.println(sendAreaText);
                    if (q1RadioButton.isSelected() || q2RadioButton.isSelected()) {
                        alertLabel.setText("");
                        new Thread(new MsgSender(send_queue, url, user, password, sendAreaText), "Name-Sender").start();
                        System.out.println(send_queue);
                        sendArea.setText("");
                    } else {
                        alertLabel.setText("必须选择一个队列!");
                    }
                }

            }
        });
//        receiveArea = new JTextArea(10, 20);

        receiveArea.setEditable(false);         //禁止编辑
        receiveArea.setLineWrap(true);          //激活自动换行功能
        receiveArea.setWrapStyleWord(true);     // 激活断行不断字功能

        int height = 10;
        Point p = new Point();
        p.setLocation(0, this.receiveArea.getLineCount() * height);
        this.scrollPane.getViewport().setViewPosition(p);
        //分别设置水平和垂直滚动条自动出现
//        scrollPane.setHorizontalScrollBarPolicy(
//                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//        scrollPane.setVerticalScrollBarPolicy(
//                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

//        int length = receiveArea.getText().length();
//        receiveArea.setCaretPosition(length);
//        scrollPane.setViewportView(receiveArea);
//        int height = 20;
//        Point p = new Point();
//        p.setLocation(0, receiveArea.getLineCount() * height);
//        scrollPane.getViewport().setViewPosition(p);
//        scrollPane.setPreferredSize(new Dimension(200,200));

//        scrollPane = new JScrollPane(receiveArea,
//                ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
//                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    }

    public static void main(String[] args) {
        String icofileName="./static/img/icon.png";
        JFrame frame = new JFrame("消息中间件-QQ聊天-张琼制作");
        //frame.setSize(500, 500);

        Toolkit tool = frame.getToolkit();    //得到一个Toolkit对象
        Image image = tool.getImage(icofileName);
        frame.setIconImage(image);          //给Frame设置图标

        frame.setContentPane(new MsgMiddleWare().panel1);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        System.out.println("开启窗体");

    }

}
