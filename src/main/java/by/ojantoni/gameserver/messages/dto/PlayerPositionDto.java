package by.ojantoni.gameserver.messages.dto;

import by.ojantoni.gameserver.messages.core.Coordinates;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PlayerPositionDto{
    public Coordinates coordinates;
    public int playerId;
}
