package space.harbour.java.hw9;

import java.util.Iterator;
import java.util.Map;

public class Container implements ObservableContainer, Iterator, Cloneable {
    private int denomination;
    private int count;
    private Container nextInChain;
    private Bank bank;

    public Container(int denomination, int count) {
        // Size of bills: 1, 5, 10, 20, 50 euros
        this.denomination = denomination;
        // Number of bills in this dispenser
        this.count = count;
    }

    public void setNextInChain(Container container) {
        nextInChain = container;
    }

    public int getDenomination() {
        return denomination;
    }

    public void setBank(Bank myBank) {
        bank = myBank;
    }

    public Container getNextInChain() {
        return nextInChain;
    }

    public int getCount() {
        return count;
    }

    public void incrementCount(int refill) {
        count = refill;
    }



    public Map<Integer, Integer> tryGiveMoney(int amount, Map<Integer, Integer> allBillsGiven) {
        // Use Chain of Responsibility to hand out the cash
        // https://java-design-patterns.com/patterns/chain/
        //return cash.handle(amount);
        int billsGiven = Math.min(amount / denomination, count);

        count -= billsGiven;
        if (count == 0) {
            //System.out.println("container " + denomination + ",count: " + count);
            //System.out.println(bank == null);
            notifyObserverBank();
        }
        amount -= billsGiven * denomination;
        if (allBillsGiven.containsKey(denomination)) {
            allBillsGiven.put(denomination, allBillsGiven.get(denomination) + billsGiven);
        } else {
            allBillsGiven.put(denomination, billsGiven);
        }

        if (nextInChain != null) {
            nextInChain.tryGiveMoney(amount, allBillsGiven);
        } else {
            if (amount > 0) {
                System.out.println("Sorry we are currently out of money."
                        + "Please try a smaller amount or another ATM nearby.");
            } else {
                System.out.println("Please take your card and your bills.");
                System.out.print("Here are your bills:\n");
                //System.out.println(allBillsGiven);
                allBillsGiven.forEach((denomination, count) ->
                        System.out.println("- " + count + "---> " + denomination + " euros bills"));
            }
        }

        return allBillsGiven;
    }


    @Override
    public boolean hasNext() {
        return (nextInChain != null);
    }

    @Override
    public Object next() {
        return nextInChain;
    }

    @Override
    protected Container clone() throws CloneNotSupportedException {
        Container result = new Container(denomination, count);
        return result;
    }

    @Override
    public void addObserverBank(ObserverBank observerBank) {
        //here we know we just have one bank looking at its own ATM
        //so no point to make a list of Observer banks
        bank = (Bank) observerBank;
    }

    @Override
    public void removeObserverBank(ObserverBank observerBank) {
        bank = null;
    }

    @Override
    public void notifyObserverBank() {
        if (bank != null) {
            bank.update(this);
        }
    }
}