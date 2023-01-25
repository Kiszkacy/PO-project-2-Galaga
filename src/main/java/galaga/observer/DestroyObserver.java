package galaga.observer;

public interface DestroyObserver extends Observer {
    void onDestroy(DestroyEvent event);
}
