package space.harbour.java.hw2;

import java.util.Scanner;

public class Pascal {

    public static int factorial(int n) {
        if (n == 1) {
            return 1;
        } else {
            return factorial(n - 1) * n;
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter size of Pascal triangle: ");
        int n = sc.nextInt();


        int[][] triangle = new int[n][n];
        triangle[0][0] = 1;
        for (int i = 1; i < n; i++) {
            triangle[i][0] = 1;
            for (int j = 1; j <= i; j++) {
                triangle[i][j] = triangle[i - 1][j - 1] + triangle[i - 1][j];
            }
        }
        int displaySize;
        int m;
        m = n / 2;
        displaySize = String.valueOf(triangle[n - 1][m]).length();

        String format = "%" + String.valueOf(displaySize) + "d";
        System.out.println("displaySize: " + displaySize);
        int[] offset = new int[n];
        offset[n - 1] = 0;
        for (int i = n - 2; i >= 0; i--) {
            offset[i] = ((n - 1 - i) * displaySize) / 2; 
            // we want at least a step of one between lines
            offset[i] = Math.max(offset[i], offset[i + 1] + 1); 
        }
        for (int i = 0; i < n; i++) {
            for (int k = 0; k < offset[i]; k++) {
                System.out.print(" ");
            }

            for (int j = 0; j <= i; j++) {
                //System.out.print(triangle[i][j]);
                System.out.printf(format, triangle[i][j]);
                System.out.print(" ");
            }
            System.out.println();

        }
    }

}
