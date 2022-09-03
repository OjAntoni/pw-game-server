package by.ojantoni.gameserver.messages.core;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
public class ActorState {
    public String id;
    public Object payload;
}
