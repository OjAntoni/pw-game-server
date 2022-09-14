package by.ojantoni.gameserver.messages.dto;

import by.ojantoni.gameserver.messages.core.Coordinates;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor
@Data
public class ObjectDto {
    private int id;
    private String name;
    private Coordinates coordinates;
}
