package space.harbour.java.hw9;

public interface ObservableContainer {
    public void addObserverBank(ObserverBank observerBank);

    public void removeObserverBank(ObserverBank observerBank);

    public void notifyObserverBank();
}
