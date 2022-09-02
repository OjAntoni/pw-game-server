package by.ojantoni.gameserver.ws;

import by.ojantoni.gameserver.messages.messages.SimpleMessage;
import by.ojantoni.gameserver.messages.messages.types.MessageType;
import by.ojantoni.gameserver.ws.resolver.MessageResolver;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class MessageHandler {
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private List<MessageResolver> messageResolversList;
    private final Map<MessageType, MessageResolver> resolvers = new HashMap<>();

    @PostConstruct
    private void init() {
        messageResolversList.forEach(r -> resolvers.put(r.getHandledMessageType(), r));
    }

    @SneakyThrows
    public void resolveMessages(WebSocketSession session, String payload) {
        log.info("Resolving " + payload);
        List<SimpleMessage> simpleMessages = mapper.readValue(payload, new TypeReference<>() {
        });
        simpleMessages.forEach(m -> resolvers.get(m.type).resolve(session, m));
    }
}
