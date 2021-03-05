package org.dreamcat.daily.script;

import java.util.Properties;
import lombok.extern.slf4j.Slf4j;
import org.dreamcat.common.hc.okhttp.OkHttpWget;
import org.dreamcat.common.util.ObjectUtil;
import org.dreamcat.common.x.mail.MailSender;

/**
 * Create by tuke on 2021/3/5
 */
@Slf4j
public class App {

    static final String MAIL_HOST = System.getenv("MAIL_HOST");
    static final String MAIL_USERNAME = System.getenv("MAIL_USERNAME");
    static final String MAIL_PASSWORD = System.getenv("MAIL_PASSWORD");
    static final String BABE_MAIL_ADDRESS = System.getenv("BABE_MAIL_ADDRESS");
    static final String MY_MAIL_ADDRESS = System.getenv("MY_MAIL_ADDRESS");

    static final MailSender sender = new MailSender(
            new Properties(), MAIL_HOST, MAIL_USERNAME, MAIL_PASSWORD);
    static final OkHttpWget wget = new OkHttpWget();

    public static void main(String[] args) throws Exception {
        log.info("host={}\tusername={}\tpassword={}",
                MAIL_HOST, MAIL_USERNAME, MAIL_PASSWORD);
        ObjectUtil.requireNonNull(MAIL_HOST, MAIL_USERNAME, MAIL_PASSWORD,
                BABE_MAIL_ADDRESS, MY_MAIL_ADDRESS);

        String url = "https://chp.shadiao.app/api.php";
        String content = wget.string(wget.get(url));
        log.info("发送：{}", content);

        sender.newOp()
                .fromPersonal("是狮子不是双子", MAIL_USERNAME)
                .toPersonal("宝藏女孩", BABE_MAIL_ADDRESS)
                .toPersonal("喜欢梨花还是海棠", MY_MAIL_ADDRESS)
                .subject("来自双鱼座的问候")
                .content(content)
                .send();
    }
}
