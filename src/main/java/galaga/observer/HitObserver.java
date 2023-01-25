package galaga.observer;

public interface HitObserver extends Observer {
    void onHit(HitEvent event);
}
