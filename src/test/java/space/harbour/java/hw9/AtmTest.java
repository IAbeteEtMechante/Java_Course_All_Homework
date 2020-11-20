package space.harbour.java.hw9;

import static org.junit.Assert.assertEquals;

import junit.framework.TestCase;
import org.junit.Test;


public class AtmTest extends TestCase {
    //test01: check that check that the first container of a clone of an ATM is similar
    //to the original
    @Test
    public void checkCloneFirstContainer() throws CloneNotSupportedException {

        //create original Atm
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


        //create the clone
        Atm cloneAtm = myAtm.clone();

        //check balance of both ATM
        assertEquals(myAtm.getBalance(), 440);
        assertEquals(cloneAtm.getBalance(), 440);

        //check denomination and count
        assertEquals(cloneAtm.firstContainer.getDenomination(), 50);
        assertEquals(cloneAtm.firstContainer.getCount(), 2);


    }

    //test02: make sure that when we clone an ATM, we also make new containers
    //which means withdraw from the clone doesnt affect the balance of the original
    @Test
    public void testClonedContainersIndependant() throws CloneNotSupportedException {

        //create original Atm
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


        //create the clone
        Atm cloneAtm = myAtm.clone();

        //check initial balance of both ATM
        assertEquals(myAtm.getBalance(), 440);
        assertEquals(cloneAtm.getBalance(), 440);

        //we withdraw from the clone
        cloneAtm.giveMeMoney(235);

        //balance changed in the new clone
        assertEquals(cloneAtm.getBalance(), 205);

        //balance stays the same in original
        assertEquals(myAtm.getBalance(), 440);


    }

    //test03: check that if a container gets empty, the bank refills it
    @Test
    public void testBankRefillsEmptyContainer() throws CloneNotSupportedException {

        //create original Atm
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

        Bank myBank = new Bank(myAtm);

        Atm bankAtm  = myBank.getNewAtm();

        //check initial balance of both ATM
        assertEquals(bankAtm.getBalance(), 440);

        //we withdraw and empty the 50s
        bankAtm.giveMeMoney(100);

        //check that the bank refilled with 10 bills the empty container:
        assertEquals(bankAtm.firstContainer.getCount(), 10);

        //balance is increment by -100 + 50 * 10 = + 400
        assertEquals(bankAtm.getBalance(), 840);


    }

    //test04: check that if a container gets empty, the bank refills it
    //but other ATMs containers dont get refilled too
    @Test
    public void testBankRefillOnlyOneContainer() throws CloneNotSupportedException {

        //create original Atm
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

        Bank myBank = new Bank(myAtm);

        Atm bankAtm  = myBank.getNewAtm();
        Atm bankAtm2 = myBank.getNewAtm();

        //check initial balance of both ATM
        assertEquals(bankAtm.getBalance(), 440);
        assertEquals(bankAtm2.getBalance(), 440);

        //we withdraw and empty the 50s
        bankAtm.giveMeMoney(100);

        //check that the bank refilled with 10 bills the empty container:
        assertEquals(bankAtm.firstContainer.getCount(), 10);
        assertEquals(bankAtm2.firstContainer.getCount(), 2);

        //balance is increment by -100 + 50 * 10 = + 400
        assertEquals(bankAtm.getBalance(), 840);


    }

    //test05: check the bank can refill several containers empty
    @Test
    public void testBankRefillsManyEmptyContainer() throws CloneNotSupportedException {

        //create original Atm
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

        Bank myBank = new Bank(myAtm);

        Atm bankAtm  = myBank.getNewAtm();

        //check initial balance of both ATM
        assertEquals(bankAtm.getBalance(), 440);

        //we withdraw and empty the 50s
        bankAtm.giveMeMoney(440);

        //check that the bank refilled with 10 bills the empty container:
        assertEquals(bankAtm.firstContainer.getCount(), 10);

        //balance should be all container denomination * 10:
        assertEquals(bankAtm.getBalance(), 1150);


    }
}