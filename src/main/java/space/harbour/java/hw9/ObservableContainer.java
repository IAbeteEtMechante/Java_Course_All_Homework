package space.harbour.java.hw9;

public interface ObservableContainer {
    public void addObserverAtm(ObserverAtm observerAtm);

    public void removeObserverAtm(ObserverAtm observerAtm);

    public void notifyObserverAtm();
}
