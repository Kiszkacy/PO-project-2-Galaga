package galaga.interfaces;

import processing.core.PImage;
import java.util.List;

public interface Animatable extends Spriteable {
    List<PImage> getSprites();
    void setSprites(List<PImage> sprites);
}
