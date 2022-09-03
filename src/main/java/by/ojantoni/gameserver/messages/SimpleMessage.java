package by.ojantoni.gameserver.messages;

import by.ojantoni.gameserver.messages.types.MessageType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SimpleMessage {
    public MessageType type;
    public String payload;
}
