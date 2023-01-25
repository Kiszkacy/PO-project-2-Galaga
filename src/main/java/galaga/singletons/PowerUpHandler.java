package galaga.singletons;


import galaga.constants.Global;
import galaga.gameObjects.powerups.PowerUp;
import galaga.observer.*;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Singleton pattern. Handles powerUp movement and powerUp deletion.
 */
public class PowerUpHandler implements DestroyObserver, Observable {

    private static final PowerUpHandler instance = new PowerUpHandler();
    private List<List<PowerUp>> powerUps;
    private List<Observer> observers;


    public static void init() {
        instance.powerUps = new ArrayList<>();
        instance.observers = new ArrayList<>();
        for(int i = 0; i < Global.collisionLayerCount; i++) {
            instance.powerUps.add(new LinkedList<>());
        }
    }


    public static boolean registerPowerUp(PowerUp powerUp) { // TODO check layer if valid
        ArrayList<Integer> layers = powerUp.getCollideLayers();
        for(int layer : layers) instance.powerUps.get(layer).add(powerUp);
        powerUp.addObserver(instance);
        return true;
    }


    public static void process(float delta) {
        for(int i = 0; i < Global.collisionLayerCount; i++) {
            List<PowerUp> layer = instance.powerUps.get(i);
            for(int j = layer.size()-1; j >= 0; j--) {
                layer.get(j).process(delta);
            }
        }
    }


    public static void draw(PApplet window) {
        for(int i = 0; i < Global.collisionLayerCount; i++) {
            for(PowerUp p : instance.powerUps.get(i)) {
                p.draw(window);
            }
        }
    }

    // overrides

    /**
     * @param event
     */
    @Override
    public void update(Event event) {
        if (event instanceof DestroyEvent)
            this.onDestroy((DestroyEvent)event);
    }

    /**
     * @param event
     */
    @Override
    public void onDestroy(DestroyEvent event) { // TODO check layer if valid
        this.notify(event);
        if (event.getDestroyed() instanceof PowerUp) {
            PowerUp powerUp = (PowerUp)event.getDestroyed();
            ArrayList<Integer> layers = powerUp.getCollideLayers();
            for(int layer : layers) instance.powerUps.get(layer).remove(powerUp);
        }
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

    private PowerUpHandler() { // no one is able to create another instance of this object

    }

    // getters/setters

    public static List<List<PowerUp>> getPowerUps() {
        return instance.powerUps;
    }


    public static PowerUpHandler getInstance() {
        return instance;
    }
}

