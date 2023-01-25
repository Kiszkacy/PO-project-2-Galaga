package galaga.gameObjects;

import galaga.interfaces.Collidable;
import galaga.interfaces.Drawable;
import galaga.interfaces.Movable;
import galaga.interfaces.Processable;
import galaga.movementTypes.LinearMovement;
import galaga.movementTypes.MovementType;
import galaga.observer.Event;
import galaga.observer.Observable;
import galaga.observer.Observer;
import processing.core.PApplet;
import processing.core.PVector;
import java.util.ArrayList;
import java.util.List;

abstract public class Entity implements Movable, Collidable, Drawable, Observable, Processable {

    protected PVector position;
    protected PVector velocity;
    protected PVector direction;
    protected ArrayList<Integer> collideLayers;
    protected PVector boundary;
    protected float acceleration;
    protected MovementType movementType;
    protected List<Observer> observers = new ArrayList<>();

    // overrides

    /**
     * @param delta
     */
    @Override
    public void process(float delta) {
        this.movementProcess(delta);
    }

    /**
     * @param delta
     */
    @Override
    public void movementProcess(float delta) {
        this.movementType.calculate(delta, this);
        this.velocity.add(this.direction.copy().mult(this.acceleration*delta));
        this.position.add(this.velocity.copy().mult(delta));
    }

    /**
     *
     */
    @Override
    abstract public void draw(PApplet window);

    /**
     * @param layers
     * @return
     */
    @Override
    public boolean addCollideLayers(ArrayList<Integer> layers) {
        for(Integer layer : layers) {
            if (this.collideLayers.contains(layer)) return false;
            this.collideLayers.add(layer);
        }
        return true;
    }

    /**
     * @param layers
     * @return
     */
    @Override
    public boolean removeCollideLayers(ArrayList<Integer> layers) {
        for(Integer layer : layers) {
            if (this.collideLayers.contains(layer)) return false;
            this.collideLayers.remove(layer);
        }

        return true;
    }

    /**
     * @param with
     * @return
     */
    @Override
    public boolean isColliding(Collidable with) {
        Collidable o = with;
        PVector op = o.getPosition();
        PVector ob = o.getBoundary();

        return (this.position.x < op.x + ob.x &&
                this.position.x + this.boundary.x > op.x &&
                this.position.y < op.y + ob.y &&
                this.boundary.y + this.position.y > op.y);
    }

    /**
     * @param observer
     * @return
     */
    @Override
    public boolean addObserver(Observer observer) {
        this.observers.add(observer); // TODO check if its already in the list
        return true;
    }

    /**
     * @param observer
     * @return
     */
    @Override
    public boolean removeObserver(Observer observer) {
        this.observers.remove(observer);
        return true;
    }

    /**
     * @param event
     */
    @Override
    public void notify(Event event) {
        for(Observer o : this.observers) o.update(event);
    }

    // constructors

    public Entity() {
        this(new PVector(0,0), new PVector(0,0), 0.0f, new PVector(0,0), new PVector(0,0), new ArrayList<>());
    }


    public Entity(PVector position, PVector boundary, ArrayList<Integer> collideLayers) {
        this(position, new PVector(0,0), 0.0f, new PVector(0,0), boundary, collideLayers);
    }


    public Entity(PVector position, PVector direction, PVector boundary, ArrayList<Integer> collideLayers) {
        this(position, new PVector(0,0), 0.0f, direction, boundary, collideLayers);
    }


    public Entity(PVector position, PVector velocity, float acceleration, PVector direction,
                  PVector boundary, ArrayList<Integer> collideLayers) {
        this(position, velocity, acceleration, direction, boundary, collideLayers, new LinearMovement());
    }


    public Entity(PVector position, PVector velocity, float acceleration, PVector direction,
                  PVector boundary, ArrayList<Integer> collideLayers, MovementType movementType) {
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.direction = direction;
        this.collideLayers = collideLayers;
        this.boundary = boundary;
        this.movementType = movementType;
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

    /**
     * @return
     */
    @Override
    public PVector getVelocity() {
        return this.velocity;
    }

    /**
     * @param velocity
     */
    @Override
    public void setVelocity(PVector velocity) {
        this.velocity = velocity;
    }

    /**
     * @return
     */
    @Override
    public PVector getDirection() {
        return this.direction;
    }

    /**
     * @param direction
     */
    @Override
    public void setDirection(PVector direction) {
        this.direction = direction;
    }

    /**
     * @return
     */
    @Override
    public ArrayList<Integer> getCollideLayers() {
        return this.collideLayers;
    }

    /**
     * @param layers
     */
    @Override
    public void setCollideLayers(ArrayList<Integer> layers) {
        this.collideLayers = layers;
    }

    /**
     * @return
     */
    @Override
    public PVector getBoundary() {
        return this.boundary;
    }

    /**
     * @param boundary
     * @return
     */
    @Override
    public boolean setBoundary(PVector boundary) {
        this.boundary = boundary;
        return true;
    }


    public float getAcceleration() {
        return this.acceleration;
    }


    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }


    public MovementType getMovementType() {
        return this.movementType;
    }


    public void setMovementType(MovementType movementType) {
        this.movementType = movementType;
    }
}
