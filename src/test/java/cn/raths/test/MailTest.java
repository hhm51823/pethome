package cn.raths.test;

import cn.raths.base.BaseTest;
import cn.raths.basic.utils.MailUtil;
import cn.raths.basic.utils.SmsUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class MailTest extends BaseTest {

    @Autowired
    private JavaMailSender javaMailSender;

    // 邮件发送简单版本
    @Test
    public void sendMailEasy() throws Exception{
        // 1.准备发送的相关信息
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        // 1.1.设置发送人
        simpleMailMessage.setFrom("1247780087@qq.com");
        // 1.2.设置接收人
        simpleMailMessage.setTo("1247780087@qq.com");
        // 1.3.设置邮件主题
        simpleMailMessage.setSubject("测试发送邮件简单版本");
        // 1.4.设置邮件文本内容
        simpleMailMessage.setText("测试成功否？");
        // 使用邮件核心对象发送邮件
        javaMailSender.send(simpleMailMessage);
    }
    // 邮件发送简单版本
    @Test
    public void sendMailEasy2() throws Exception{
        String to = "1170912437@qq.com";
        String subject = "店铺入驻请求被拒绝";
        String contnet = "太有问题了，我拒绝";
        MailUtil.sendSimpleMail(to, subject, contnet);
    }

    // 邮件发送简单版本
    @Test
    public void sms() throws Exception{
        String phone = "15281052629";
        String msg = "神奇不神奇！！1+1等于2";
        SmsUtils.SendMsg(phone, msg);
    }
}
