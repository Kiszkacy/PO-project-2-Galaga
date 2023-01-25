package galaga.interfaces;

import processing.core.PImage;

public interface Spriteable extends Drawable {
    String getSpriteIdentifier();
    void setSpriteIdentifier(String spriteIdentifier);
    PImage getSprite();
    void setSprite(PImage sprite);
}
