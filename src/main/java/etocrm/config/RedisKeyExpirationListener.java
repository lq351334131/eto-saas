package etocrm.config;

import lombok.extern.slf4j.Slf4j;
import org.etocrm.database.enums.ResponseEnum;
import org.etocrm.server.ScanQRCodeServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class RedisKeyExpirationListener implements MessageListener {



    @Autowired
    private ScanQRCodeServer scanQRCodeServer;
    @Value("${spring.redis.database}")
    private String redisDataBase;
    @Override
    public void onMessage(Message message, byte[] pattern) {
        String channelStr = new String(message.getChannel());
        String expiredKey = channelStr.replace("__" + "keyspace@" + redisDataBase + "__", "");
        if(expiredKey.contains("ticket")) {
            expiredKey = expiredKey.substring(("ticket:".length()));
            log.info("失效的ticket:{}"+expiredKey);
            scanQRCodeServer.responseCodeMessage(expiredKey, ResponseEnum.FAILD.getCode(),"二维码已失效");
        }
    }
}