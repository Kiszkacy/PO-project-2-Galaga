package galaga.constants;

import static galaga.util.EasyPrint.p;

public enum Layers {
    PLAYER_PROJECTILES(0),
    PLAYER(0),
    ENEMY_PROJECTILES(1),
    ENEMY(1),
    POWERUP(2);

    private final int id;

    public int id() {
        return this.id;
    }

    Layers(int id) {
        this.id = id;
    }
}
