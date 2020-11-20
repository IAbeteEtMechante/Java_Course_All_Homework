package space.harbour.java.hw9;

import java.util.List;

public class Bank implements MyObserver {
    List<Atm> atms;
    Atm prototypeAtm;

    public Atm getNewAtm() throws CloneNotSupportedException {
        // How about observer?
        Atm newAtm = prototypeAtm.clone();
        atms.add(newAtm);
        return newAtm;
    }

    public Atm getAtm(int index) {
        return atms.get(index);
    }

    public int getNumberOfAtms() {
        return atms.size();
    }

    public int getTotalBalance() {
        return atms.parallelStream().mapToInt(atm -> atm.getBalance()).sum();
    }

    @Override
    public void update(MyObservable observable, String message) {
        System.out.println(message);
        // Maybe do something about it?
        // Send a visitor to refill?
    }
}
