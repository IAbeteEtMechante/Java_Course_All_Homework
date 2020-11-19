package space.harbour.java.hw2;

import java.util.Scanner;

public class Slang {

    public static String fixAbbreviations(String myString) {

        String replaceString = myString.replaceAll("PLZ", "please");
        replaceString = replaceString.replaceAll("FYI", "for your information");
        replaceString = replaceString.replaceAll("GTFO", "please, leave me alone");
        replaceString = replaceString.replaceAll("ASAP", "as soon as possible");
        return replaceString;
    }

    public static String fixSmiles(String myString) {

        String replaceString = myString.replaceAll(":\\)", "[smiling]");
        replaceString = replaceString.replaceAll(":\\(", "[sad]");
        replaceString = replaceString.replaceAll("¯\\\\_\\(ツ\\)_/¯", "[such a life]");
        return replaceString;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter your text:");
        String myString = sc.nextLine();
        System.out.println("Here is the modified text:");
        System.out.println(fixAbbreviations(fixSmiles(myString)));

    }
}
