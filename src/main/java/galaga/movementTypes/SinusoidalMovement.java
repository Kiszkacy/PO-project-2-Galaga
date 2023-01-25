package galaga.movementTypes;

import galaga.interfaces.Movable;
import processing.core.PVector;

public class SinusoidalMovement implements MovementType {

    private float time = 0.0f;
    private float frequency;
    private float amplitude;
    private float speed;

    @Override
    public void calculate(float delta, Movable object) {
        this.time += delta;
        PVector dir = object.getDirection().copy().normalize();
        PVector up = new PVector(-dir.y, dir.x);
        float upSpeed = (float)Math.cos((double)this.time*this.frequency) * this.amplitude;

        object.setVelocity(up.mult(upSpeed).add(dir.mult(this.speed)));
    }

    // constructors

    public SinusoidalMovement(float frequency, float amplitude, float speed) {
        this.frequency = frequency;
        this.amplitude = amplitude;
        this.speed = speed;
    }

    // getters/setters

    public float getFrequency() {
        return this.frequency;
    }


    public void setFrequency(float frequency) {
        this.frequency = frequency;
    }


    public float getAmplitude() {
        return this.amplitude;
    }


    public void setAmplitude(float amplitude) {
        this.amplitude = amplitude;
    }


    public float getSpeed() {
        return this.speed;
    }


    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
