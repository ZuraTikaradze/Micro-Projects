package ge.biterium.chat;



import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.io.StringWriter;
import java.util.*;
/**
 * Created by Zura Tikaradze
 */
@ServerEndpoint(value = "/chatroomServerEndpoint/{chatroom}", configurator = ChatroomServerConfigurator.class)
public class ChatroomServerEndpoint {

    static Map<String, Set<Session>> chatrooms = Collections.synchronizedMap(new HashMap<>());

    public Set<Session> getChatroom(String chatroomName) {
        Set<Session> chatroom = chatrooms.get(chatroomName);
        if (chatroom == null) {
            chatroom = Collections.synchronizedSet(new HashSet<>());
            chatrooms.put(chatroomName, chatroom);
        }
        return chatroom;
    }

    @OnMessage
    public void handleMessage(final String message, Session userSession) {
        final String username = (String) userSession.getUserProperties().get("username");
        String chatroom = (String) userSession.getUserProperties().get("chatroom");
        Set<Session> chatroomUsers = getChatroom(chatroom);
        chatroomUsers.stream().forEach(x -> {
            try {
                x.getBasicRemote().sendText(buildJsonData(username, message));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @OnOpen
    public void handleOpen(EndpointConfig config, Session userSession, @PathParam("chatroom") String chatroom) {
        userSession.getUserProperties().put("username", config.getUserProperties().get("username"));
        userSession.getUserProperties().put("chatroom", chatroom);
        Set<Session> chatroomUser = getChatroom(chatroom);
        chatroomUser.add(userSession);
    }

    @OnClose
    public void handleClose(Session userSession) {
        String chatroom = (String) userSession.getUserProperties().get("chatroom");
        Set<Session> chatroomUsers = getChatroom(chatroom);
        chatroomUsers.remove(userSession);
    }

    private String buildJsonData(String username, String mesaage) {
        JsonObject jsonObject = Json.createObjectBuilder().add("message", username + ": " + mesaage).build();
        StringWriter stringWriter = new StringWriter();
        try (JsonWriter jsonWriter = Json.createWriter(stringWriter)) {
            jsonWriter.write(jsonObject);
        }
        return stringWriter.toString();
    }
}
