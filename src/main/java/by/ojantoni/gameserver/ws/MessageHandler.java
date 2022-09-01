package by.ojantoni.gameserver.ws;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;
import web.messages.SimpleMessage;
import web.messages.types.MessageTypes;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MessageHandler {
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private List<MessageResolver> messageResolversList;
    private final Map<MessageTypes,MessageResolver> resolvers = new HashMap<>();

    @PostConstruct
    private void init(){
        messageResolversList.forEach(r -> resolvers.put(r.getHandledMessageType(), r));
    }

    @SneakyThrows
    public void resolveMessages(WebSocketSession session, String payload) {
        List<SimpleMessage> simpleMessages = mapper.readValue(payload, new TypeReference<List<SimpleMessage>>() {});
        //simpleMessages.forEach( m -> );
    }
}
