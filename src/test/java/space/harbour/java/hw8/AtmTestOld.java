package space.harbour.java.hw9;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.Before;
import org.junit.Test;

public class AtmTestOld {

    // Test01: test that if cash = null, then Balance = 0
    // and giveMeMoney still works, but rejects all requests
    @Test
    public void testNoContainers() {
        Atm myAtm = new Atm(null);
        assertEquals(myAtm.getBalance(), 0);
        for (int i = 0; i < 20; i++) {
            assertEquals(myAtm.giveMeMoney(i * 5), null);

        }
    }

    // Test02: test that balance is working properly before and after a withdrowal
    @Test
    public void testBalance() {
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
        assertEquals(myAtm.getBalance(), 440);
        myAtm.giveMeMoney(235);
        assertEquals(myAtm.getBalance(), 205);
    }

    // Test03: test the correct number of bills given
    //by the givememoney() method
    @Test
    public void testGiveMeMoney() {
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
        assertEquals(myAtm.getBalance(), 440);
        myAtm.giveMeMoney(235);
        assertEquals(myAtm.allBillsGiven.get(50), Integer.valueOf(2));
        assertEquals(myAtm.allBillsGiven.get(20), Integer.valueOf(6));
        assertEquals(myAtm.allBillsGiven.get(10), Integer.valueOf(1));
        assertEquals(myAtm.allBillsGiven.get(5), Integer.valueOf(1));



    }

    //Test04: check that if it is empty it doesnt give anything
    @Test
    public void testCanBreakTheBank() {

        for (int i = 0; i < 1000; i += 5) {

            Container container50d01c = new Container(50, 0);
            Container container50d02c = new Container(20, 0);
            container50d01c.setNextInChain(container50d02c);
            Container container20d01c = new Container(20, 0);
            container50d02c.setNextInChain(container20d01c);
            Container container10d01c = new Container(10, 0);
            container20d01c.setNextInChain(container10d01c);
            Container container10d02c = new Container(10, 0);
            container10d01c.setNextInChain(container10d02c);
            Container container05d01c = new Container(5, 0);
            container10d02c.setNextInChain(container05d01c);

            Atm myAtm = new Atm(container50d01c);
            assertEquals(myAtm.getBalance(), 0);
            myAtm.giveMeMoney(i);
            assertEquals(myAtm.getBalance(), 0);

            assertEquals(myAtm.allBillsGiven.get(50), Integer.valueOf(0));
            assertEquals(myAtm.allBillsGiven.get(20), Integer.valueOf(0));
            assertEquals(myAtm.allBillsGiven.get(10), Integer.valueOf(0));
            assertEquals(myAtm.allBillsGiven.get(5), Integer.valueOf(0));

        }




    }



}
