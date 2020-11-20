package space.harbour.java.hw9;

import java.util.HashMap;
import java.util.Map;

public class Atm implements Cloneable {
    // Make sure you can have 2x 20 euro dispensers in a chain
    public Container firstContainer;
    public Map<Integer, Integer> allBillsGiven;

    public Atm(Container firstContainer) {
        this.firstContainer = firstContainer;
        this.allBillsGiven = new HashMap<>();
    }

    public Container getFirstContainer() {
        return firstContainer;
    }

    public int getBalance() {
        // User ITERATOR pattern to get balance of the ATM
        // https://java-design-patterns.com/patterns/iterator/

        if (firstContainer == null) {
            return 0;
        }
        //start with the first one when non null
        Container currentContainer = firstContainer;

        //what we want to return
        int result = currentContainer.getDenomination() * currentContainer.getCount();
        while (currentContainer.hasNext()) {
            currentContainer = (Container) currentContainer.next();
            result += currentContainer.getDenomination() * currentContainer.getCount();
        }
        return result;
    }

    public Map<Integer, Integer> giveMeMoney(int amount) {
        // Use Chain of Responsibility to hand out the cash
        // https://java-design-patterns.com/patterns/chain/
        //return cash.handle(amount);
        if (firstContainer == null) {
            System.out.println("Sorry we are currently out of money."
                    + "Please try a smaller amount or another ATM nearby.");
            return null;
        }
        return firstContainer.tryGiveMoney(amount, allBillsGiven);
    }


    @Override
    public Atm clone() throws CloneNotSupportedException {
        Container clonedFirstContainer = firstContainer.clone();
        Atm result = new Atm(clonedFirstContainer);
        Container clonedCurrentContainer = clonedFirstContainer;
        Container nextContainer = firstContainer.getNextInChain();
        while (nextContainer != null) {
            Container clonedNextContainer = nextContainer.clone();
            clonedCurrentContainer.setNextInChain(clonedNextContainer);
            clonedCurrentContainer = clonedCurrentContainer.getNextInChain();
            nextContainer = nextContainer.getNextInChain();

        }
        return result;
    }


    public static void main(String[] args) {
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

    }
}
