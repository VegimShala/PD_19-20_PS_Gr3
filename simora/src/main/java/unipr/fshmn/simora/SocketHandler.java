package unipr.fshmn.simora;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.*;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.*;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;

@Component
public class SocketHandler extends TextWebSocketHandler {

    List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    ObjectMapper objectMapper = new ObjectMapper();


    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException {
        for (WebSocketSession webSocketSession : sessions) {
            if (webSocketSession.isOpen() && !session.getId().equals(webSocketSession.getId())) {
                JsonNode jsonNode = objectMapper.readTree(message.getPayload());
                if(((UsernamePasswordAuthenticationToken)webSocketSession.getPrincipal()).getAuthorities().stream().anyMatch(ga -> ga.getAuthority().equals("ROLE_ADMIN"))&&jsonNode.get("type").toString().equals("\"offer\"")) {
                    webSocketSession.sendMessage(message);
                }else{
                    if(!jsonNode.get("type").toString().equals("\"offer\"")) {
                        webSocketSession.sendMessage(message);
                    }
                }

            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
    }
}