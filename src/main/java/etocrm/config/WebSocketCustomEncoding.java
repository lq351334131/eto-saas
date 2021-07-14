package etocrm.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.etocrm.database.util.ResponseVO;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

@Slf4j
public class WebSocketCustomEncoding implements Encoder.Text<ResponseVO> {
@Override  
public String encode(ResponseVO responseVO) {
return JSON.toJSONString(responseVO);
}

@Override
public void init(EndpointConfig endpointConfig) {
  log.info("Init");
}

@Override
public void destroy() {
    log.info("destroy");
}
} 