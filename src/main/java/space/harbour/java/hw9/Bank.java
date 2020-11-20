package space.harbour.java.hw9;

import java.util.ArrayList;
import java.util.List;

public class Bank implements ObserverBank {
    List<Atm> atms;
    Atm prototypeAtm;

    Bank(Atm prototype) {
        prototypeAtm = prototype;
        atms = new ArrayList<>();
        atms.add(prototypeAtm);
    }

    public Atm getNewAtm() throws CloneNotSupportedException {
        // How about observer?
        Atm newAtm = prototypeAtm.clone();

        ////add bank as a variable to all new containers for convenience (Observer)
        //newAtm.firstContainer.setBank(this);
        //Container currentContainer = newAtm.firstContainer;
        //while (currentContainer.hasNext()) {
        //    currentContainer = (Container) currentContainer.next();
        //    currentContainer.setBank(this);
        //}

        //add bank as a variable to all new containers for convenience (Observer)
        newAtm.firstContainer.addObserverBank(this);
        Container currentContainer = newAtm.firstContainer;
        while (currentContainer.hasNext()) {
            currentContainer = (Container) currentContainer.next();
            currentContainer.addObserverBank(this);
        }

        atms.add(newAtm);
        return newAtm;
    }

    public void setPrototypeAtm(Atm prototype) {
        prototypeAtm = prototype;
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
    public void update(ObservableContainer observable) {
        System.out.println("Thanks for letting me know your container is empty.");
        refill((Container) observable);
    }

    public void refill(Container container) {
        container.incrementCount(10);
    }

    public static void main(String[] args) throws CloneNotSupportedException {

        Container container50d01c = new Container(50, 2);
        Container container50d02c = new Container(20, 5);
        container50d01c.setNextInChain(container50d02c);
        Container container20d01c = new Container(20, 2);
        container50d02c.setNextInChain(container20d01c);
        Container container10d01c = new Container(10, 2);
        container20d01c.setNextInChain(container10d01c);
        Container container10d02c = new Container(10, 8);
        container10d01c.setNextInChain(container10d02c);
        Container container05d01c = new Container(5, 20);
        container10d02c.setNextInChain(container05d01c);


        Atm myAtm = new Atm(container50d01c);
        System.out.println("Hello, the maximum amount you can withdraw is: "
                + myAtm.getBalance() + " euros");
        myAtm.giveMeMoney(235);
        System.out.println("New balance is: " + myAtm.getBalance() + " euros.\nBye Bye!");


        Bank myBank = new Bank(myAtm);

        myBank.getNewAtm();





    }
}
