package galaga.observer;

import galaga.constants.InputMode;
import galaga.util.Trio;

import java.util.Objects;

public class InputEvent implements Event {

    private final int keyCode;
    private final InputMode inputMode;
    private final int eventType;

    // overrides

    /**
     * @return
     */
    @Override
    public Object[] getAllData() {
        return new Object[]{this.keyCode, this.inputMode, this.eventType};
    }

    // constructors

    public InputEvent(int keyCode, InputMode inputMode, int eventType) {
        this.keyCode = keyCode;
        this.inputMode = inputMode;
        this.eventType = eventType;
    }

    public InputEvent(Trio<Integer, InputMode, Integer> trio) {
        this.keyCode = trio.first;
        this.inputMode = trio.second;
        this.eventType = trio.third;
    }

    // getters/setters

    public int getKeyCode() {
        return this.keyCode;
    }


    public InputMode getInputMode() {
        return this.inputMode;
    }


    public int getEventType() {
        return this.eventType;
    }

    // hash & equals

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InputEvent that = (InputEvent) o;
        return this.keyCode == that.keyCode && this.eventType == that.eventType && this.inputMode == that.inputMode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.keyCode, this.inputMode, this.eventType);
    }
}
