package by.ojantoni.gameserver.actors.wilk;

import by.ojantoni.gameserver.messages.core.Coordinates;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WilkState {
    private Coordinates coordinates;
    private boolean isAlive;
}
