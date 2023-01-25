package galaga.constants;

public enum EnemyType {
    SMALL("SMALL"),
    SHOOTER("SHOOTER"),
    TANKY("TANKY"),
    SPEEDY("SPEEDY");

    private final String id;

    public String id() {
        return this.id;
    }

    EnemyType(String id) {
        this.id = id;
    }
}
