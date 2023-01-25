package galaga.gameObjects;

import galaga.interfaces.Placeable;
import galaga.interfaces.Spriteable;
import galaga.singletons.SpriteHandler;
import galaga.util.Color;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import static galaga.util.EasyPrint.pcol;

public class Sprite implements Spriteable, Placeable {

    protected PVector position;
    protected PImage sprite;
    protected String spriteIdentifier;


    protected void init() {
        if (SpriteHandler.getSprite(this.spriteIdentifier) != null)
            this.sprite = SpriteHandler.getSprite(this.spriteIdentifier).get(0);
    }

    // overrides

    /**
     * @param window
     */
    @Override
    public void draw(PApplet window) {
        window.image(this.sprite, this.position.x, this.position.y);
    }

    // constructors

    public Sprite(String spriteIdentifier, PVector position) {
        this.spriteIdentifier = spriteIdentifier;
        this.position = position;
        this.init();
    }

    // getters/setters

    /**
     * @return
     */
    @Override
    public String getSpriteIdentifier() {
        return this.spriteIdentifier;
    }

    /**
     * @param spriteIdentifier
     */
    @Override
    public void setSpriteIdentifier(String spriteIdentifier) {
        this.spriteIdentifier = spriteIdentifier;
    }

    /**
     * @return
     */
    @Override
    public PImage getSprite() {
        return this.sprite;
    }

    /**
     * @param sprite
     */
    @Override
    public void setSprite(PImage sprite) {
        this.sprite = sprite;
    }

    /**
     * @return
     */
    @Override
    public PVector getPosition() {
        return this.position;
    }

    /**
     * @param position
     */
    @Override
    public void setPosition(PVector position) {
        this.position = position;
    }
}
