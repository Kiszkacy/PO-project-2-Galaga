package galaga.UI;

import galaga.interfaces.Drawable;
import galaga.interfaces.Placeable;
import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

import java.util.function.Function;

public class Button implements Placeable, Drawable {

    private PVector position;
    private PVector size;
    private String text;
    private boolean isHovered = false;

    // overrides

    /**
     * @param window
     */
    @Override
    public void draw(PApplet window) {
        window.noStroke();
        window.fill(255, 127);
        window.rectMode(PConstants.CENTER);
        window.textAlign(PConstants.CENTER, PConstants.CENTER);
        if (isHovered) {
            window.rect(this.position.x, this.position.y, this.size.x+96.0f, this.size.y+48.0f, 32.0f);
            window.fill(255, 255);
            window.textSize(80.0f);
            window.text(this.text, this.position.x, this.position.y-8.0f);
        } else {
            window.rect(this.position.x, this.position.y, this.size.x, this.size.y, 32.0f);
            window.fill(255, 255);
            window.textSize(64.0f);
            window.text(this.text, this.position.x, this.position.y-8.0f);
        }
    }

    // constructors

    public Button(PVector position, PVector size, String text) {
        this.position = position;
        this.size = size;
        this.text = text;
    }

    // getters/setters

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


    public PVector getSize() {
        return this.size;
    }


    public void setSize(PVector size) {
        this.size = size;
    }


    public String getText() {
        return this.text;
    }


    public void setText(String text) {
        this.text = text;
    }


    public boolean isHovered() {
        return this.isHovered;
    }


    public void setHovered(boolean hovered) {
        this.isHovered = hovered;
    }
}
