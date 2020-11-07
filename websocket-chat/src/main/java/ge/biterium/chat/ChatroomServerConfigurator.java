package ge.biterium.chat;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
/**
 * Created by Zura Tikaradze
 */
public class ChatroomServerConfigurator extends ServerEndpointConfig.Configurator {
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response){
        sec.getUserProperties().put("username",((HttpSession) request.getHttpSession()).getAttribute("username"));
    }
}
