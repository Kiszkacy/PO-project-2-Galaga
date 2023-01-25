package galaga.singletons;


import galaga.constants.Global;
import galaga.gameObjects.projectiles.Projectile;
import galaga.observer.*;
import galaga.util.Color;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static galaga.util.EasyPrint.pcol;

/**
 * Singleton pattern. Handles projectile movement and projectile deletion.
 */
public class ProjectileHandler implements DestroyObserver, Observable {

    private static final ProjectileHandler instance = new ProjectileHandler();
    private List<List<Projectile>> projectiles;
    private List<Observer> observers;


    public static void init() {
        instance.projectiles = new ArrayList<>();
        instance.observers = new ArrayList<>();
        for(int i = 0; i < Global.collisionLayerCount; i++) {
            instance.projectiles.add(new LinkedList<>());
        }
    }


    public static boolean registerProjectile(Projectile projectile) { // TODO check layer if valid
        ArrayList<Integer> layers = projectile.getCollideLayers();
        for(int layer : layers) instance.projectiles.get(layer).add(projectile);
        projectile.addObserver(instance);
        return true;
    }


    public static void process(float delta) {
        for(int i = 0; i < Global.collisionLayerCount; i++) {
            List<Projectile> layer = instance.projectiles.get(i);
            for(int j = layer.size()-1; j >= 0; j--) {
                layer.get(j).process(delta);
            }
        }
    }


    public static void draw(PApplet window) {
        for(int i = 0; i < Global.collisionLayerCount; i++) {
            for(Projectile p : instance.projectiles.get(i)) {
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
        if (event.getDestroyed() instanceof Projectile) {
            Projectile projectile = (Projectile)event.getDestroyed();
            ArrayList<Integer> layers = projectile.getCollideLayers();
            for(int layer : layers) instance.projectiles.get(layer).remove(projectile);
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

    private ProjectileHandler() { // no one is able to create another instance of this object

    }

    // getters/setters

    public static List<List<Projectile>> getProjectiles() {
        return instance.projectiles;
    }


    public static ProjectileHandler getInstance() {
        return instance;
    }
}

