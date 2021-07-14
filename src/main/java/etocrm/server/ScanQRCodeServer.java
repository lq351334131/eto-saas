package etocrm.server;

import lombok.extern.slf4j.Slf4j;
import org.etocrm.config.WebSocketCustomEncoding;
import org.etocrm.database.enums.ResponseEnum;
import org.etocrm.database.util.ResponseVO;
import org.etocrm.model.user.LoginUserVO;
import org.springframework.stereotype.Controller;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

@Controller
@Slf4j
@ServerEndpoint(value = "/ws/qrcode",subprotocols = {"protocol"},encoders = WebSocketCustomEncoding.class)
public class ScanQRCodeServer{

    private static ConcurrentHashMap<String, Session> sockets =  new ConcurrentHashMap<>();


    @OnOpen
    public void onOpen(Session session) {
        String ticket= session.getQueryString();
        ticket = ticket.substring("ticket".length() + 1);
        sockets.put(ticket, session);
    }

    @OnClose
    public void onClose(Session session) {
        String ticket= session.getQueryString();
        ticket = ticket.substring("ticket".length() + 1);
        sockets.remove(ticket);
    }

    @OnMessage
    public void onMessage(String ticket){
        Session session = sockets.get(ticket);
        ResponseVO<String> s2 = ResponseVO.success(ResponseEnum.FAILD.getCode(), "扫码成功");

        try {
            //主动推送
           session.getBasicRemote().sendObject(s2);

        } catch (Exception e) {
           log.info(e.getMessage());
        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        String ticket= session.getQueryString();
        ticket = ticket.substring("ticket".length() + 1);
        sockets.remove(ticket);
    }

    public void response(String ticket, String message) throws IOException {
        Session session = sockets.get(ticket);
        if(session!=null) {
            session.getBasicRemote().sendText(message);
        }
    }


    public void responseToekn(String ticket,Integer code, LoginUserVO loginUserVO)  {
        Session session = sockets.get(ticket);
        if(session!=null) {
            try {
                session.getBasicRemote().sendObject(ResponseVO.success(loginUserVO));
            } catch (IOException e) {
                log.info(e.getMessage());
            } catch (EncodeException e) {
                log.info(e.getMessage());
            }
        }
    }

    public void responseCodeMessage(String ticket,Integer code, String message)  {
        Session session = sockets.get(ticket);
        if(session!=null) {
            try {
                //主动推送
                session.getBasicRemote().sendObject(ResponseVO.success(code, message));
            } catch (Exception e) {
                log.info(e.getMessage());
            }
        }
    }

}
