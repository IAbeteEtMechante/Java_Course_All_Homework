package space.harbour.java.hw1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;

public class Homework1 {
    private static int[] readFile() {
        try {
            Scanner sc = new Scanner(new File("src/main/java/hw1/input.txt"));
            int a = sc.nextInt();
            int b = sc.nextInt();
            return new int[]{a, b};
        } catch (FileNotFoundException ex) {
            return null;
        }
    }

    private static void writeFile(int number) {
        try {
            Writer writer = new BufferedWriter(
                    new OutputStreamWriter(
                            new FileOutputStream("src/main/java/hw1/output.txt"), "ascii"));
            writer.write(Integer.valueOf(number).toString());
            writer.close();
        } catch (IOException ex) {
            System.out.println("IO Exception error.");
        }
    }


    public static void main(String[] args) {
        int[] vals = readFile();
        if (vals == null) {
            System.out.println("You didn't provide an input.txt file, "
                    + "please add an input.txt file to this folder and retry.'");
        } else {
            long mult = vals[0] * vals[1];
            writeFile((int) mult);
        }
    }
}


