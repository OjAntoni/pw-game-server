package by.ojantoni.gameserver.ws;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageResolver {
    @Autowired
    private ObjectMapper mapper;

    public void resolveMessages(String payload) {
    }
}
