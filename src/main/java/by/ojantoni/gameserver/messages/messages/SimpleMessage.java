package by.ojantoni.gameserver.messages.messages;

import by.ojantoni.gameserver.messages.messages.types.MessageType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class SimpleMessage {
    public MessageType type;
    public String payload;
}
