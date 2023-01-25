package galaga.engine.spawner;

import galaga.constants.EnemyType;
import galaga.constants.MovementTypeIdentifier;
import galaga.movementTypes.MovementType;
import processing.core.PVector;

public class EnemySpawn {

    public EnemyType enemyType;
    public float buffedChance;
    public float delay;
    public MovementTypeIdentifier movementTypeIdentifier;
    public PVector offset;


    public EnemySpawn(EnemyType enemyType, float buffedChance, float delay, MovementTypeIdentifier movementTypeIdentifier, PVector offset) {
        this.enemyType = enemyType;
        this.buffedChance = buffedChance;
        this.delay = delay;
        this.movementTypeIdentifier = movementTypeIdentifier;
        this.offset = offset;
    }
}
