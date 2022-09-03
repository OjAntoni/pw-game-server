package by.ojantoni.gameserver.messages.core;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ActorState {
    public String id;
    public Object payload;
}
