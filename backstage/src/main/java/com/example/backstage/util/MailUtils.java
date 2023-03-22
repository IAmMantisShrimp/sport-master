package com.example.backstage.util;

import com.example.backstage.vo.MailVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;

/**
 * @author HuaRunSheng
 * @date 2022/11/9 14:49
 * @description :
 */
@Slf4j
@Component
public class MailUtils {
    @Resource
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;

    /**
     * 发送邮件
     * @param mail: 发送邮件的内容对象
     * @return
     */
    public String sendMail(MailVo mail) {
        try {
            //Html格式,如果有图片或视频
            if (mail.isHtml()) {
                // 构建文件对象 ,以大文件上传,内容最大为10G
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                // 设置是否支持多文件
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
                // 收件人
                messageHelper.setTo(mail.getReceivers());
                //    发件人
                messageHelper.setFrom(from);
                messageHelper.setSubject(mail.getSubject());
                messageHelper.setText(mail.getContent(), true);
                mailSender.send(mimeMessage);
                log.info("邮件发送成功! 收件人---{}----", Arrays.asList(mail.getReceivers()));

            } else {
                //    纯文本 格式,可以直接发送
                //    创建邮件对象
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                //    发件人
                mailMessage.setFrom(from);
                // 收件人
                mailMessage.setTo(mail.getReceivers());
                //    邮件主题
                mailMessage.setSubject(mail.getSubject());
                //    邮件内容
                mailMessage.setText(mail.getContent());
                // 发送邮件
                mailSender.send(mailMessage);
                log.info("邮件发送成功! 收件人---{}----", Arrays.asList(mail.getReceivers()));

            }
            return "邮件发送成功";
        } catch (Exception e) {
            log.error("邮件发送失败-->{}", e.getMessage());
            return "邮件发送失败";
        }
    }

}
