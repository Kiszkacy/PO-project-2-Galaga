package galaga.singletons;


import galaga.constants.Global;
import galaga.constants.Layers;
import galaga.gameObjects.Ship;
import galaga.observer.*;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Singleton pattern. Handles ship movement and ship deletion.
 */
public class ShipHandler implements DestroyObserver, Observable {

    private static final ShipHandler instance = new ShipHandler();
    private List<List<Ship>> ships;
    private List<Observer> observers;


    public static void init() {
        instance.ships = new ArrayList<>();
        instance.observers = new ArrayList<>();
        for(int i = 0; i < Global.collisionLayerCount; i++) {
            instance.ships.add(new LinkedList<>());
        }
    }


    public static boolean registerShip(Ship ship) { // TODO check layer if valid
        ArrayList<Integer> layers = ship.getCollideLayers();
        for(int layer : layers) instance.ships.get(layer).add(ship);
        ship.addObserver(instance);
        return true;
    }


    public static void process(float delta) {
        for(int i = 0; i < Global.collisionLayerCount; i++) {
            List<Ship> layer = instance.ships.get(i);
            for(int j = layer.size()-1; j >= 0; j--) {
                layer.get(j).process(delta);
            }
        }
    }


    public static void draw(PApplet window) {
        for(int i = 0; i < Global.collisionLayerCount; i++) {
            for(Ship s : instance.ships.get(i)) {
                s.draw(window);
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
        if (event.getDestroyed() instanceof Ship) {
            Ship ship = (Ship)event.getDestroyed();
            ArrayList<Integer> layers = ship.getCollideLayers();
            for(int layer : layers) instance.ships.get(layer).remove(ship);
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

    private ShipHandler() { // no one is able to create another instance of this object

    }

    // getters/setters

    public static List<List<Ship>> getShips() {
        return instance.ships;
    }


    public static Ship getPlayerShip() {
        if (instance.ships.get(Layers.PLAYER.id()).size() > 0)
            return instance.ships.get(Layers.PLAYER.id()).get(0);
        return null;
    }


    public static ShipHandler getInstance() {
        return instance;
    }
}

