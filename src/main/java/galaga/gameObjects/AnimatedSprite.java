package galaga.gameObjects;

import galaga.interfaces.Animatable;
import galaga.interfaces.Destroyable;
import galaga.observer.DestroyEvent;
import galaga.observer.Event;
import galaga.observer.Observable;
import galaga.observer.Observer;
import galaga.singletons.SpriteHandler;
import galaga.util.Color;
import processing.core.PApplet;
import processing.core.PImage;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

import static galaga.util.EasyPrint.pcol;

public class AnimatedSprite extends Sprite implements Animatable, Destroyable, Observable {

    protected List<PImage> sprites = new ArrayList<>();
    protected int activeSpriteIdx = 0;
    protected int framesActive = 0;
    protected int currentFramesActive = 0;
    protected boolean oneTime = false;
    private List<Observer> observers = new ArrayList<>();


    protected void nextFrame() {
        this.currentFramesActive = 0;
        this.sprite = this.sprites.get(this.activeSpriteIdx);
        if (this.oneTime && this.activeSpriteIdx == this.sprites.size()-1) this.destroy();
        this.activeSpriteIdx = (this.activeSpriteIdx+1) % this.sprites.size();
    }

    // overrides

    /**
     *
     */
    @Override
    protected void init() {
        if (SpriteHandler.getSprite(this.spriteIdentifier) != null) {
            this.sprites = SpriteHandler.getSprite(this.spriteIdentifier);
            this.nextFrame();
        }
    }

    /**
     * @param window
     */
    @Override
    public void draw(PApplet window) {
        super.draw(window);
        if (this.currentFramesActive >= this.framesActive)  this.nextFrame();
        else                                                this.currentFramesActive += 1;
    }

    /**
     *
     */
    @Override
    public void destroy() {
        this.notify(new DestroyEvent(this));
    }

    /**
     * @param observer
     * @return
     */
    @Override
    public boolean addObserver(Observer observer) {
        return this.observers.add(observer);
    }

    /**
     * @param observer
     * @return
     */
    @Override
    public boolean removeObserver(Observer observer) {
        return this.observers.remove(observer);
    }

    /**
     * @param event
     */
    @Override
    public void notify(Event event) {
        for (Observer o : this.observers) o.update(event);
    }

    // constructors

    public AnimatedSprite(String spriteIdentifier, PVector position, int framesActive) {
        super(spriteIdentifier, position);
        this.framesActive = framesActive;
        this.oneTime = false;
        this.init();
    }

    public AnimatedSprite(String spriteIdentifier, PVector position, int framesActive, boolean oneTime) {
        super(spriteIdentifier, position);
        this.framesActive = framesActive;
        this.oneTime = oneTime;
        this.init();
    }

    // getters/setters

    /**
     * @return
     */
    @Override
    public List<PImage> getSprites() {
        return this.sprites;
    }

    /**
     * @param sprites
     */
    @Override
    public void setSprites(List<PImage> sprites) {
        this.sprites = sprites;
        this.nextFrame();
    }
}
