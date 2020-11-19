package space.harbour.java.hw2;

public class EstimateSize {

    public static void runGarbageCollection() throws InterruptedException {

        System.gc();
        Thread.sleep(10);

    }

    public static long usedMemory() {

        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }

    public static float estimateIntSize() throws InterruptedException {
        runGarbageCollection();
        long occupiedBeforeAllocation = usedMemory();

        int n = 10_000_000;
        int[] myArray = new int[n];
        for (int i = 0; i < n; i++) {

            myArray[i] = i;
        }
        long occupiedAfterAllocation = usedMemory();

        return (occupiedAfterAllocation - occupiedBeforeAllocation) / Float.valueOf(n);
    }

    public static float estimateReferenceSize() throws InterruptedException {
        runGarbageCollection();
        long occupiedBeforeAllocation = usedMemory();

        int n = 10_000_000;
        Integer[] myIntegerArray = new Integer[n];
        for (int i = 0; i < n; i++) {
            // we assign to null, so there is on Object header
            // and there is no Object value
            // they dont occupy any space except the reference itself
            myIntegerArray[i] = null;
        }
        long occupiedAfterAllocation = usedMemory();

        return (occupiedAfterAllocation - occupiedBeforeAllocation) / Float.valueOf(n);
    }

    public static float estimateObjectSize() throws InterruptedException {
        runGarbageCollection();
        long occupiedBeforeAllocation = usedMemory();

        int n = 10_000_000;
        Object[] myObjectArray = new Object[n];
        for (int i = 0; i < n; i++) {
            // we assign to null, so there is on Object header
            // and there is no Object value
            // they dont occupy any space except the reference itself
            myObjectArray[i] = new Object();
        }
        long occupiedAfterAllocation = usedMemory();

        return (occupiedAfterAllocation - occupiedBeforeAllocation) / Float.valueOf(n);
    }

    public static float estimateStringSize() throws InterruptedException {
        runGarbageCollection();
        long occupiedBeforeAllocation = usedMemory();

        int n = 10_000_000;
        String[] myStringArray = new String[n];
        for (int i = 0; i < n; i++) {

            myStringArray[i] = String.valueOf(i);
        }
        long occupiedAfterAllocation = usedMemory();

        return (occupiedAfterAllocation - occupiedBeforeAllocation) / Float.valueOf(n);
    }

    public static void main(String[] args) throws InterruptedException {
        runGarbageCollection();
        long mem = usedMemory();
        System.out.println("Estimate of the size of an int: " + estimateIntSize());
        System.out.println("Estimate of the size of a reference: " + estimateReferenceSize());
        System.out.println("Estimate of the size of an Object: " + estimateObjectSize());
        System.out.println("Estimate of the size of a String: " + estimateStringSize());

    }

}
