package by.ojantoni.gameserver.actors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CoordinatesDto {
    public float x;
    public float y;
    public String id;

    public CoordinatesDto(float x, float y) {
        this.x = x;
        this.y = y;
    }

}
