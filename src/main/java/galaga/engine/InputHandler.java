package galaga.engine;

import galaga.constants.InputMode;
import galaga.observer.Event;
import galaga.observer.InputEvent;
import galaga.observer.Observer;
import galaga.util.Color;
import galaga.util.Trio;

import java.util.*;

import static galaga.util.EasyPrint.p;
import static galaga.util.EasyPrint.pcol;

public class InputHandler implements galaga.observer.Observable { // TODO SOMEHOW LOOK FOR DELTA? / PAUSE INPUTS UNTIL NEW FRAME?

    private int bufferSize;
    private Map<Trio<Integer, InputMode, Integer>, Integer> keyMap;
    private List<galaga.observer.Observer> observers;

    public boolean setupKey(int keyCode, InputMode inputMode, int eventType) {
        if (keyMap.containsKey(new Trio<>(keyCode, inputMode, eventType))) return false;

        this.keyMap.put(new Trio<>(keyCode, inputMode, eventType), -1); // TODO -1 is not enough -bufferSize ?
        return true;
    }


    public boolean registerEvent(int keyCode, InputMode inputMode, int eventType, int time) {
        if (!keyMap.containsKey(new Trio<>(keyCode, inputMode, eventType))) return false;
        this.keyMap.put(new Trio<>(keyCode, inputMode, eventType), time); // overwrites old entry !
        return true;
    }


    public void processEvents(InputMode inputMode, int time) {
        List<Map.Entry<Trio<Integer, InputMode, Integer>, Integer>> sorted = new ArrayList<>(keyMap.entrySet());
        sorted.sort((o1, o2) -> {
            if (o1.getKey().third > o2.getKey().third) return 1;
            return -1;
        });
        for (Map.Entry<Trio<Integer, InputMode, Integer>, Integer> entry : sorted) {
            if (!entry.getKey().second.equals(inputMode)) continue;
            if (!(time - entry.getValue() < this.bufferSize)) continue;
            this.notify(new InputEvent(entry.getKey()));
        }
    }

    // overrides

    /**
     * @param observer
     * @return
     */
    @Override
    public boolean addObserver(galaga.observer.Observer observer) { // TODO
        this.observers.add(observer);
        return true;
    }

    /**
     * @param observer
     * @return
     */
    @Override
    public boolean removeObserver(galaga.observer.Observer observer) { // TODO
        this.observers.remove(observer);
        return true;
    }

    /**
     * @param event
     */
    @Override
    public void notify(Event event) {
        for (Observer o : this.observers) o.update(event);
    }

    // constructors

    public InputHandler() {
        this.bufferSize = 24; //ms
        this.keyMap = new HashMap<>();
        this.observers = new ArrayList<>();
    }

    // getters/setters

    public Map<Trio<Integer, InputMode, Integer>, Integer> getKeyMap() {
        return this.keyMap;
    }
}
