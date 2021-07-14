package etocrm.utils;

import cn.hutool.extra.mail.MailAccount;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailUtils {

    @Value("${mail.username}")
    private String userName;

    @Value("${mail.password}")
    private String password;

    public MailAccount getMailAccount(){
        MailAccount account = new MailAccount();
        account.setHost("smtp.exmail.qq.com");
        account.setPort(465);
        account.setAuth(true);
        account.setFrom("alerts@etocrm.com");
        account.setUser(userName);
        account.setPass(password);
        account.setSslEnable(true);
        return account;
    }
}