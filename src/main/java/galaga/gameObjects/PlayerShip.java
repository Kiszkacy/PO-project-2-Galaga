package galaga.gameObjects;

import galaga.constants.InputMode;
import galaga.constants.Layers;
import galaga.interfaces.Shootable;
import galaga.observer.Event;
import galaga.observer.InputEvent;
import galaga.observer.InputObserver;
import galaga.util.Color;
import galaga.util.InputsConfig;
import processing.core.PApplet;
import processing.core.PVector;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import static galaga.constants.Global.maxPlayerOverheal;
import static galaga.util.EasyPrint.p;
import static galaga.util.EasyPrint.pcol;

public class PlayerShip extends Ship implements InputObserver {

    protected boolean shooting = false;
    protected boolean flyingLeft = false;
    protected boolean flyingRight = false;
    protected boolean flyingUp = false;
    protected boolean flyingDown = false;

    // overrides

    /**
     * @param delta
     */
    @Override
    public void process(float delta) {
        super.process(delta);
        if (this.shooting) this.shoot();
//        pcol(Color.VIOLET, this.flyingLeft);
//        pcol(Color.BLUE, this.flyingRight);
    }

    /**
     * @param delta
     */
    @Override
    public void movementProcess(float delta) {
        this.isFlying = (this.flyingLeft || this.flyingRight || this.flyingUp || this.flyingDown);
        this.direction = new PVector(0,0);
        if (this.flyingLeft) this.direction.add(new PVector(-1,0));
        if (this.flyingRight) this.direction.add(new PVector(1,0));
        if (this.flyingUp) this.direction.add(new PVector(0,-1));
        if (this.flyingDown) this.direction.add(new PVector(0,1));
        this.direction.y *= 0.75f; // some funny behavior
        this.direction.x *= 1.33f;
        this.direction.normalize();
        super.movementProcess(delta);
    }

    /**
     * @param event
     */
    @Override
    public void update(Event event) {
        if (event instanceof InputEvent)
            this.onInput((InputEvent)event);
    }

    /**
     * @param event
     */
    @Override
    public void onInput(InputEvent event) { // TODO here we skip delta calculations and that's why movement is "weird"
        if (!event.getInputMode().equals(InputMode.INGAME)) return;
        String action = KeyEvent.getKeyText(event.getKeyCode());

        // TODO this is stupid its 2nd time that the key is being checked
        if (event.getEventType() == processing.event.KeyEvent.RELEASE) {
            if (InputsConfig.getLeftKey().contains(action)) {
                this.flyingLeft = false;
            } else if (InputsConfig.getRightKey().contains(action)) {
                this.flyingRight = false;
            } else if (InputsConfig.getUpKey().contains(action)) {
                this.flyingUp = false;
            } else if (InputsConfig.getDownKey().contains(action)) {
                this.flyingDown = false;
            } else if (InputsConfig.getShootKey().contains(action)) {
                this.shooting = false;
            }
        } else if (event.getEventType() == processing.event.KeyEvent.PRESS) {
            if (InputsConfig.getLeftKey().contains(action)) {
                this.flyingLeft = true;
            } else if (InputsConfig.getRightKey().contains(action)) {
                this.flyingRight = true;
            } else if (InputsConfig.getUpKey().contains(action)) {
                this.flyingUp = true;
            } else if (InputsConfig.getDownKey().contains(action)) {
                this.flyingDown = true;
            }  else if (InputsConfig.getShootKey().contains(action)) {
                this.shooting = true;
            }
        }
    }

    // getters/setters

    /**
     * @param amount
     */
    @Override
    public void heal(float amount) {
        this.setHealth(Math.min(maxPlayerOverheal, this.health+amount));
    }


    // constructors

    public PlayerShip() {
        super();
        this.collideLayers.add(Layers.PLAYER.id());
    }

    
    public PlayerShip(float maxHealth, float maxSpeed, float acceleration, float deceleration, Shootable gun,
                PVector shotDirection, String spriteName) {
        super(maxHealth, maxSpeed, acceleration, deceleration, gun, shotDirection, spriteName);
        this.collideLayers.add(Layers.PLAYER.id());
    }


    public PlayerShip(float maxHealth, float maxSpeed, float acceleration, float deceleration, Shootable gun,
                PVector shotDirection, String spriteName, int spriteFrames) {
        super(maxHealth, maxSpeed, acceleration, deceleration, gun, shotDirection, spriteName, spriteFrames);
        this.collideLayers.add(Layers.PLAYER.id());
    }


    public PlayerShip(PVector position, PVector boundary, ArrayList<Integer> collideLayers, float maxHealth, float maxSpeed,
                float acceleration, float deceleration, Shootable gun,
                PVector shotDirection, String spriteName) {
        super(position, boundary, collideLayers, maxHealth, maxSpeed, acceleration, deceleration, gun, shotDirection, spriteName);
        this.collideLayers.add(Layers.PLAYER.id());
    }


    public PlayerShip(PVector position, PVector boundary, ArrayList<Integer> collideLayers, float maxHealth, float maxSpeed,
                float acceleration, float deceleration, Shootable gun,
                PVector shotDirection, String spriteName, int spriteFrames) {
        super(position, boundary, collideLayers, maxHealth, maxSpeed, acceleration, deceleration, gun, shotDirection, spriteName, spriteFrames);
        this.collideLayers.add(Layers.PLAYER.id());
    }


    public PlayerShip(PVector position, PVector direction, PVector boundary, ArrayList<Integer> collideLayers, float maxHealth, float maxSpeed,
                float acceleration, float deceleration, Shootable gun,
                PVector shotDirection, String spriteName) {
        super(position, direction, boundary, collideLayers, maxHealth, maxSpeed, acceleration, deceleration, gun, shotDirection, spriteName);
        this.collideLayers.add(Layers.PLAYER.id());
    }


    public PlayerShip(PVector position, PVector direction, PVector boundary, ArrayList<Integer> collideLayers, float maxHealth, float maxSpeed,
                float acceleration, float deceleration, Shootable gun,
                PVector shotDirection, String spriteName, int spriteFrames) {
        super(position, direction, boundary, collideLayers, maxHealth, maxSpeed, acceleration, deceleration, gun, shotDirection, spriteName, spriteFrames);
        this.collideLayers.add(Layers.PLAYER.id());
    }
}
