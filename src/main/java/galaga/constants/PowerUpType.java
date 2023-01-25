package galaga.constants;

public enum PowerUpType {
    HEALTH("HEALTH"),
    ARMOR("ARMOR"),
    DAMAGE("DAMAGE"),
    MACHINE("MACHINE"),
    SHOTGUN("SHOTGUN");

    private final String id;

    public String id() {
        return this.id;
    }

    PowerUpType(String id) {
        this.id = id;
    }
}
