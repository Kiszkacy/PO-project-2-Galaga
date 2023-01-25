package galaga.constants;

import galaga.movementTypes.MovementType;

public enum MovementTypeIdentifier {
    LINEAR("LINEAR"),
    SINUSOIDAL("SINUSOIDAL");

    private final String id;

    public String id() {
        return this.id;
    }

    MovementTypeIdentifier(String id) {
        this.id = id;
    }
}
