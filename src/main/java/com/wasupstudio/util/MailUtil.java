package com.wasupstudio.util;

import com.wasupstudio.constant.ProjectConstant;
import com.wasupstudio.enums.MailEnum;
import lombok.extern.slf4j.Slf4j;

import javax.mail.*;
import javax.mail.internet.*;
import java.text.SimpleDateFormat;
import java.util.Properties;

@Slf4j
public class MailUtil {

    private static String user = "paul.lee.2022.09@gmail.com";
    private static String pwd = "ogeplmjxvktfyefd";
    private static String from = "paul.lee.2022.09@gmail.com";
    private static final String MAIL_FORGET_VERIFY_URL = "https://wasupstudionobullying.com/wasupstudio/auth/reset/";
//    private static final String MAIL_FORGET_VERIFY_URL = "http://localhost:8080/wasupstudio/auth/reset/";
    private static final String MAIL_SIGNUP_VERIFY_URL = "https://wasupstudionobullying.com/wasupstudio/auth/verify/";
//    private static final String MAIL_SIGNUP_VERIFY_URL = "http://localhost:8080/wasupstudio/auth/verify/";

    private static final String MAIL_FORGET_URL = "https://wasupstudionobullying.com/wasupstudio/wasupstudio/api/forget";

    public static void sendMail(String action, String content, String mailTo) throws Exception {
        try {

            user = "paul.lee.2022.09@gmail.com";
            pwd = "ogeplmjxvktfyefd";
            Session mailSession = Session.getInstance(setMailProperties(), new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(user, pwd);
                }
            });


            // 開啟Session的debug模式，這樣就可以查看到程序發送Email的運行狀態
            mailSession.setDebug(false);

            // 產生整封 email 的主體 message
            MimeMessage message = new MimeMessage(mailSession);

            // 文字部份，注意 img src 部份要用 cid:接下面附檔的header
            MimeBodyPart textPart = new MimeBodyPart();
            StringBuffer html = new StringBuffer();
            // 帳號註冊
            if (action.equals(ProjectConstant.MailType.SIGNUP)) {
                message.setSubject(MailEnum.MAIL_SUBTITLE_SINGN.getDesc());
                html = mainContent(String.valueOf(signupContent(content)));

            }
            // 忘記密碼
            if (action.equals(ProjectConstant.MailType.FORGET)) {
                message.setSubject(MailEnum.MAIL_SUBTITLE_FORGET.getDesc());
                html = mainContent(String.valueOf(forgetContent(content)));
            }
            // 啟動碼寄送
            if (action.equals(ProjectConstant.MailType.START_KEY)) {
                message.setSubject(MailEnum.MAIL_SUBTITLE_START_KEY.getDesc());
            }

            textPart.setContent(html.toString(), "text/html; charset=UTF-8");

            // 圖檔部份，注意 html 用 cid:image，則header要設<image>
            Multipart email = new MimeMultipart();
            email.addBodyPart(textPart);
            message.setContent(email);
            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from, mailTo));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(mailTo));
            // Send message
            Transport.send(message);

            log.info("[發送 {} 信件 成功] >> from:{}, to:{}", action, from, mailTo);
        } catch (MessagingException e) {
            log.error("[發送 {} 信件 失敗] >> from:{}, to:{}\n exception:{}", action, from, mailTo, e);
            e.printStackTrace();
        }
    }

    public static Properties setMailProperties() {
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtps");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.timeout", "30000");
        props.put("mail.smtp.connectiontimeout", "10000");
        props.put("mail.smtp.ssl.trust", "*");
        props.put("mail.smtp.debug", "true");
        props.put("mail.smtp.socketFactory.fallback", "false");
        return props;
    }

    public static void main(String[] args) {
        String verificationCode = "your_verification_code";
        StringBuffer emailContent = mainContent(forgetContent(verificationCode).toString());
        System.out.println(emailContent.toString());
    }

    public static StringBuffer mainContent(String content) {
        StringBuffer html = new StringBuffer();
        // 開始構建HTML內容
        html.append(
            "<html><body width='100%' style='margin: 0; padding: 0 !important; background: #f3f3f5; mso-line-height-rule: exactly;'>" +
            "    <center style='width: 100%; background: #f3f3f5;'>" +
            "        <div style='text-align: left; padding: 20px; font-family: Arial, sans-serif;'>");
        html.append(content);
        html.append(
            "            <p><strong>本郵件為自動發送，請勿做任何回復</strong></p>" +
            "            <p>若有任何問題，請聯繫 wasupstudio@gmail.com</p>" +
            "        </div>" +
            "        <div style='text-align: center; background-color: #f3f3f5; padding: 10px;'>" +
            "            <p>---------------------------------------------------------------------------------------</p>" +
            "            <p>我們班的叢林法則: <a href='https://wasupstudionobullying.com/'>https://wasupstudionobullying.com</a></p>" +
            "            <p>阿普蛙工作室: <a href='www.wasupstudio.com'>www.wasupstudio.com</a></p>" +
            "            <p>---------------------------------------------------------------------------------------</p>" +
            "        </div>" +
            "    </center>" +
            "</body></html>"
        );
        return html;
    }
    public static StringBuffer signupContent(String verificationCode) {
        StringBuffer html = new StringBuffer();
        // 獲取當前日期和時間
        String currentDate = DateUtils.getCurrentDateTimeFormatted();
        // 格式化日期和時間為字符串
        // 開始構建HTML內容
        html.append(
            "            <p>您好，</p>" +
            "            <p>您於 " + currentDate + " 時，申請帳號</p>" +
            "            <p><strong>若您沒有申請此需求，請忽略此封信</strong></p>" +
            "            <p>請您點擊下方鏈接進行帳號啟用：</p>" +
            "               <a class='s-btn s-btn__primary' href='" + MAIL_SIGNUP_VERIFY_URL + verificationCode + "' " +
            "               target='_parent' style='background: #0095FF; " +
            "               border: 1px solid #0077cc; box-shadow: inset 0 1px 0 0 rgba(102,191,255,.75); " +
            "               font-family: arial, sans-serif; font-size: 17px; line-height: 17px; color: #ffffff; " +
            "               text-align: center; text-decoration: none; padding: 13px 17px; display: block; border-radius: 4px; " +
            "               white-space: nowrap;'>" +
            "               啟用連結請點我</a>"
        );
        return html;
    }

    public static StringBuffer forgetContent(String verificationCode) {
        StringBuffer html = new StringBuffer();
        // 獲取當前日期和時間
        String currentDate = DateUtils.getCurrentDateTimeFormatted();
        // 開始構建HTML內容
        html.append(
            "            <p>您好，</p>" +
            "            <p>您於 " + currentDate + " 時，申請帳號密碼重新設定</p>" +
            "            <p><strong>若您沒有申請此需求，請忽略此封信</strong></p>" +
            "            <p>請您點擊下方鏈接進行密碼重新設置：</p>" +
            "               <a class='s-btn s-btn__primary' href='" + MAIL_FORGET_VERIFY_URL + verificationCode + "' " +
            "               target='_parent' style='background: #0095FF; " +
            "               border: 1px solid #0077cc; box-shadow: inset 0 1px 0 0 rgba(102,191,255,.75); " +
            "               font-family: arial, sans-serif; font-size: 17px; line-height: 17px; color: #ffffff; " +
            "               text-align: center; text-decoration: none; padding: 13px 17px; display: block; border-radius: 4px; " +
            "               white-space: nowrap;'>" +
            "               啟用連結請點我</a>"
        );
        return html;
    }
}