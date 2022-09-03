package by.ojantoni.gameserver.actors.jstar;

import by.ojantoni.gameserver.messages.core.Coordinates;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class JstarState {
    private Coordinates coordinates;
    private boolean normalJstar;
    private boolean stoppedJstar;
}
