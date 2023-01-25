package galaga.engine;

import galaga.constants.Layers;
import galaga.gameObjects.Ship;
import galaga.gameObjects.powerups.PowerUp;
import galaga.observer.Event;
import galaga.observer.HitEvent;
import galaga.observer.Observable;
import galaga.observer.Observer;
import galaga.singletons.PowerUpHandler;
import galaga.singletons.ProjectileHandler;
import galaga.singletons.ShipHandler;
import galaga.gameObjects.projectiles.Projectile;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public class CollisionHandler implements Observable {

    private List<galaga.observer.Observer> observers = new ArrayList<>();

    public void process(float delta) { // TODO use delta for collisions one frame ahead ?
        // player projectiles with enemies
        List<Projectile> playerProjectiles = ProjectileHandler.getProjectiles().get(Layers.PLAYER_PROJECTILES.id());
        List<Ship> enemyShips = ShipHandler.getShips().get(Layers.ENEMY.id());

        for(int j = playerProjectiles.size()-1; j >= 0; j--) {
            Projectile p = playerProjectiles.get(j);
            for(int i = enemyShips.size()-1; i >= 0; i--) {
                Ship e = enemyShips.get(i);
                if (!p.isColliding(e)) continue;
                this.notify(new HitEvent(e, p, e.getPosition().copy().add(p.getPosition().copy()).mult(0.5f)));
                e.damage(p.getDamage());
                p.getCollisionHistory().add(e);
                p.setPenetrationLeft(p.getPenetrationLeft()-1);
            }
        }

        // enemy projectiles with player
        Ship playerShip = ShipHandler.getPlayerShip();
        if (playerShip == null) return;
        List<Projectile> enemyProjectiles = ProjectileHandler.getProjectiles().get(Layers.ENEMY_PROJECTILES.id());

        for(int i = enemyProjectiles.size()-1; i >= 0; i--) {
            Projectile p = enemyProjectiles.get(i);
            if (!p.isColliding(playerShip)) continue;
            this.notify(new HitEvent(playerShip, p, playerShip.getPosition().copy().add(p.getPosition().copy()).mult(0.5f)));;
            playerShip.damage(enemyProjectiles.get(i).getDamage());
            p.getCollisionHistory().add(playerShip);
            p.setPenetrationLeft(p.getPenetrationLeft()-1);
        }

        // enemies with player
        for(int i = enemyShips.size()-1; i >= 0; i--) {
            Ship e = enemyShips.get(i);
            if (!e.isColliding(playerShip)) continue;
            playerShip.damage(1.0f);
            enemyShips.get(i).destroy();
            this.notify(new HitEvent(playerShip, e, playerShip.getPosition().copy().add(e.getPosition().copy()).mult(0.5f)));
        }
        
        // powerups with plater
        List<PowerUp> powerUps = PowerUpHandler.getPowerUps().get(Layers.POWERUP.id());
        for(int i = powerUps.size()-1; i >= 0; i--) {
            PowerUp p = powerUps.get(i);
            if (!p.isColliding(playerShip)) continue;
            p.activateOn(playerShip);
            p.destroy();
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
        for(Observer o : this.observers) o.update(event);
    }
}
