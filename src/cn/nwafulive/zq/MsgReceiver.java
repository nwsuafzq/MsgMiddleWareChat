package cn.nwafulive.zq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import javax.swing.*;

/**
 * @Author ZhangQiong nwsuafzq@hotmail.com
 * @Date 2017/4/7
 * @Time 19:52.
 */
public class MsgReceiver implements Runnable {
    private String url;
    private String user;
    private String password;
    private final String QUEUE;
    private JTextArea receiveArea;

    public MsgReceiver(String queue, String url, String user, String password, JTextArea receiveArea) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.QUEUE = queue;
        this.receiveArea = receiveArea;
    }


    @Override
    public void run() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                user, password, url);
        Session session = null;
        Destination receiveQueue;
        try {
            Connection connection = connectionFactory.createConnection();

            session = connection
                    .createSession(true, Session.SESSION_TRANSACTED);
            receiveQueue = session.createQueue(QUEUE);
            MessageConsumer consumer = session.createConsumer(receiveQueue);

            connection.start();

            while (true) {
                Message message = consumer.receive();

                if (message instanceof TextMessage) {
                    TextMessage receiveMessage = (TextMessage) message;
                    System.out.println("我是Receiver,收到消息如下: \r\n"
                            + receiveMessage.getText());

                    String receiveMsg = receiveMessage.getText();
                    //receiveArea.setLineWrap(true);
                    receiveArea.append(receiveMsg + "\r\n");

//                    receiveArea.setLineWrap(true);        //激活自动换行功能
//                    receiveArea.setWrapStyleWord(true);     // 激活断行不断字功能
//                    receiveArea.setEditable(false);

                } else {
                    session.commit();
                    break;
                }

            }
            connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}

