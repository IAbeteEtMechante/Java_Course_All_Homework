package space.harbour.java.hw9;

public interface MyObservable {
    public void addOberser(MyObserver observer);

    public void removeObserver(MyObserver observer);

    public void notifyObservers(String message);
}
