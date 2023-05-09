package com.wasupstudio.util;

import com.wasupstudio.constant.ProjectConstant;
import com.wasupstudio.enums.MailEnum;
import org.springframework.beans.factory.annotation.Value;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

public class MailUtil {

	@Value("${MAIL_USERNAME}")
	private String user ;
	@Value("${MAIL_PASSWORD}")
	private String pwd;
	@Value("${MAIL_USER}")
	private String from;
	private static final String MAIL_SIGNUP_URL = "http://localhost:8080/wasupstudio/api/signup";
	private static final String MAIL_FORGET_URL = "http://localhost:8080/wasupstudio/api/forget";

	public void sendMail(String action, String memUuid, String mailTo) throws Exception {
		try {

			Session mailSession = Session.getInstance(setMailProperties(), new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user, pwd);
				}
			});

			// 開啟Session的debug模式，這樣就可以查看到程序發送Email的運行狀態
//			mailSession.setDebug(true);

			// 產生整封 email 的主體 message
			MimeMessage message = new MimeMessage(mailSession);

			// 文字部份，注意 img src 部份要用 cid:接下面附檔的header
			MimeBodyPart textPart = new MimeBodyPart();
			StringBuffer html = new StringBuffer();
			if (action.equals("apiuser")) {
				message.setSubject(MailEnum.MAIL_SUBTITLE_SINGN.getDesc());
				html = mailContent_signUp(memUuid);
			}
			if (action.equals("forget")) {
				message.setSubject(MailEnum.MAIL_SUBTITLE_FORGET.getDesc());
				html = mailContent_Forget(memUuid);
			}

			if (action.equals("report_send")) {
				message.setSubject(MailEnum.MAIL_SUBTITLE_REPORT_SEND.getDesc());
				html = mailContent_Report_Send(memUuid);
			}

			if (action.equals("report_rec")) {
				message.setSubject(MailEnum.MAIL_SUBTITLE_REPORT_REC.getDesc());
				html = mailContent_Report_Rec(memUuid);
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

		} catch (AddressException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public void sendMailToAdmin(String name, String mail, String content) {
		// 設定傳送基本資訊
		String mailTo = user;
		try {

			Session mailSession = Session.getInstance(setMailProperties(), new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(user, pwd);
				}
			});

			// 開啟Session的debug模式，這樣就可以查看到程序發送Email的運行狀態
//			mailSession.setDebug(true);

			// 產生整封 email 的主體 message
			MimeMessage message = new MimeMessage(mailSession);

			// 文字部份，注意 img src 部份要用 cid:接下面附檔的header
			MimeBodyPart textPart = new MimeBodyPart();
			StringBuffer html = new StringBuffer();
			message.setSubject(MailEnum.MAIL_SUBTITLE_CONNECT.getDesc());
			html = mailContent_Connect(name, mail, content);
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

		} catch (AddressException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public Properties setMailProperties() {
		Properties props = new Properties();
		props.put("mail.transport.protocol", "smtps");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.host", "mail.gandi.net");
		props.put("mail.smtp.timeout", "30000");
		props.put("mail.smtp.connectiontimeout", "10000");
		props.put("mail.smtp.ssl.trust", "*");
		props.put("mail.smtp.debug", "true");
		props.put("mail.smtp.socketFactory.fallback", "false");
		return props;
	}

	public StringBuffer mailContent_Forget(String memUuid) {
		StringBuffer html = new StringBuffer();
		html.append("<h2>忘記密碼</h2><br>");
		html.append("<p>請盡速修改你的密碼</p><");
		html.append("<p><a href='" + MAIL_FORGET_URL + memUuid + "'>啟用請點擊連結</a></p><br>");
		html.append("<img src='cid:image'/><br>");
		return html;
	}

	public StringBuffer mailContent_signUp(String memUuid) {
		StringBuffer html = new StringBuffer();
		html.append(
				"<body width='100%' style='margin: 0; padding: 0 !important; background: #f3f3f5; mso-line-height-rule: exactly;'>"
						+ "    <center style='width: 100%; background: #f3f3f5;'>"
						+ "        <div class='email-container' style='max-width: 680px; margin: 0 auto;'>"
						+ "            <table border='0' cellpadding='0' cellspacing='0' role='presentation' style='max-width: 680px; width:100%'>"
						+ "                <tr>"
						+ "                    <td style='padding: 20px 30px; text-align: left;' class='sm-px'>"
						+ "                        <a href='https://sugarbabytw.com/dist/'>"
						+ "                            <img src='https://sugarbabytw.com/sweetNetImg/images_dashboard/logo.png' border='0' height='36' width='146' style='display: block; font-family: arial, sans-serif; font-size: 15px; line-height: 15px; color: #3C3F44; margin: 0;'>"
						+ "                        </a></td>"
						+ "                </tr><tr>"
						+ "                    <td style='padding: 30px; background-color: #ffffff;' class='sm-p bar'>"
						+ "                        <table border='0' cellpadding='0' cellspacing='0' role='presentation' style='width:100%;'>"
						+ "                            <tr>"
						+ "                                <td style='padding-bottom: 15px; font-family: arial, sans-serif; font-size: 15px; line-height: 21px; color: #3C3F44; text-align: left;'>"
						+ "                                    <h1 style='font-weight: bold; font-size: 27px; line-height: 27px; color: #0C0D0E; margin: 0 0 15px 0;'>歡迎加入S&B！</h1>"
						+ "                                    <strong>感謝註冊S&B，記得定期回訪喔！</strong>"
						+ "                                    <ul style='padding: 0; margin: 0; list-style-type: disc;'>"
						+ "                                        <li style='margin:0 0 10px 30px;'>新加入會員</li>"
						+ "                                    </ul></td>"
						+ "                            </tr><tr>"
						+ "                                <td>"
						+ "                                    <table align='left' border='0' cellpadding='0' cellspacing='0' role='presentation'>"
						+ "                                        <tr>"
						+ "                                            <td class='s-btn s-btn__primary' style='border-radius: 4px; background: #0095ff;'>"
						+ "                                                <a class='s-btn s-btn__primary' href='" + MAIL_SIGNUP_URL + memUuid + "' target='_parent' style='background: #0095FF; border: 1px solid #0077cc; box-shadow: inset 0 1px 0 0 rgba(102,191,255,.75); font-family: arial, sans-serif; font-size: 17px; line-height: 17px; color: #ffffff; text-align: center; text-decoration: none; padding: 13px 17px; display: block; border-radius: 4px; white-space: nowrap;'>啟用連結請點我</a>"
						+ "                                            </td>"
						+ "                                        </tr>"
						+ "                                    </table>"
						+ "                                </td></tr>"
						+ "                        </table></td>"
						+ "                </tr><tr>"
						+ "                    <td style='padding: 30px;' class='sm-p'>"
						+ "                        <table align='left' border='0' cellpadding='0' cellspacing='0' role='presentation' width='100%'>"
						+ "                            <tr>"
						+ "                                <td style='padding: 30px 0;' width='100%' class='sm-py'>"
						+ "                                    <table aria-hidden='true' border='0' cellpadding='0' cellspacing='0' role='presentation' style='width:100%'>"
						+ "                                        <tr>"
						+ "                                            <td height='1' width='100%' style='font-size: 0; line-height: 0; border-top: 1px solid #D6D8DB;'>&nbsp;</td>"
						+ "                                        </tr>"
						+ "                                    </table>"
						+ "                                </td></tr>"
						+ "                            <tr>"
						+ "                                <td style='padding-bottom: 5px; text-align: left;'>"
						+ "                                    <img src='https://sugarbabytw.com/sweetNetImg/images_dashboard/logo.png' align='left' alt='' border='0' width='111' height='22' style='display: block; font-family: arial, sans-serif; font-size: 12px; line-height: 12px; color: #3C3F44;'>"
						+ "                                </td></tr>"
						+ "                            <tr>"
						+ "                                <td style='padding-bottom: 5px; font-size: 12px; line-height: 15px; font-family: arial, sans-serif; color: #9199A1; text-align: left;'><strong>S&B</strong>, <span class='unstyle-auto-detected-links'>Copyright@S&B</span></td>"
						+ "                            </tr><tr>"
						+ "                                <td>"
						+ "                                    <table align='left' border='0' cellpadding='0' cellspacing='0' role='presentation' style='background:#ffffff; border-radius:1px; line-height:0px'>"
						+ "                                        <tr>"
						+ "                                            <td>"
						+ "                                                <div style='border-radius: 1px; border: 1px solid #D6D9DC; padding: 4px 6px 3px 6px; font-size: 11px; line-height: 11px; font-family: Consolas, monospace; color: #E06C77;' title='because we care'>&lt;3</div>"
						+ "                                            </td>"
						+ "                                        </tr>"
						+ "                                    </table>"
						+ "                                </td></tr>"
						+ "                        </table></td>"
						+ "                </tr></table></div>"
						+ "    </center></body></html>");
		return html;
	}

	public StringBuffer mailContent_Connect(String name, String mail, String content) {
		StringBuffer html = new StringBuffer();
		html.append("<h3>稱呼：" + name + "</h3><br>");
		html.append("<h3>聯絡信箱：" + mail + "</h3><br>");
		html.append("<h3>聯繫內容：</h3><br>");
		html.append("<div>" + content + "</div><br>");
		return html;
	}

	public StringBuffer mailContent_Report_Send(String memUuid) {
		StringBuffer html = new StringBuffer();
		html.append("<h2>您檢舉的案件已經成立</h2><br>");
		html.append("<h3>版主會盡速處理</h3><br>");
		html.append("<img src='cid:image'/><br>");

		return html;
	}

	public StringBuffer mailContent_Report_Rec(String memUuid) {
		StringBuffer html = new StringBuffer();
		html.append("<h2>您的行為可能有些不妥</h2><br>");
		html.append("<h3>請檢查是否有不當留言，或是不當的照片</h3><br>");
		html.append("<img src='cid:image'/><br>");

		return html;
	}

}