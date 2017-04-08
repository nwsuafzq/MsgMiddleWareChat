package cn.nwafulive.zq;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author ZhangQiong nwsuafzq@hotmail.com
 * @Date 2017/4/7
 * @Time 19:52.
 */
public class MsgSender implements Runnable {
    private String url;
    private String user;
    private String password;
    private final String QUEUE;

    private String sendAreaText;            /* 获取sendArea消息发送文本框中的信息 */

    public MsgSender(String queue, String url, String user, String password, String sendAreaText) {
        this.url = url;
        this.user = user;
        this.password = password;
        this.QUEUE = queue;
        this.sendAreaText = sendAreaText;
    }

    @Override
    public void run() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                user, password, url);
        Session session = null;
        Destination sendQueue;
        Connection connection = null;
        try {
            connection = connectionFactory.createConnection();

            connection.start();


            //while(true) {

//            String sendtext = MsgMiddleWare.sendarea;
//            System.out.println(sendtext);


            System.out.println(sendAreaText);

            //while (sendtext != null ) {

            session = connection.createSession(true,
                    Session.SESSION_TRANSACTED);

            sendQueue = session.createQueue(QUEUE);
            MessageProducer sender = session.createProducer(sendQueue);
            TextMessage outMessage = session.createTextMessage();

            Date today = new Date();
            SimpleDateFormat dateTimeFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = dateTimeFormat.format(today);

            outMessage.setText("----" + date + "----" + "\n" + "对方说: " + sendAreaText);


            sender.send(outMessage);

            session.commit();

            sender.close();
            //  }
            connection.close();
            // }
        } catch (JMSException e) {
            e.printStackTrace();
        } finally {
            try {

                if (connection != null) {
                    connection.close();
                }
                if (session != null) {
                    session.close();
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }

//            if ((++messageCount) == 10) {
//                // 发够十条消息退出
//                break;
//            }
//            Thread.sleep(1000);
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

    public String getQUEUE() {
        return QUEUE;
    }
}


