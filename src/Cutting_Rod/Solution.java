package Cutting_Rod;

import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Solution {
    static class Pair {
        private int[] optimalCuts;
        private int[] optimalRevenue;

        public Pair() {

        }

        public Pair(int[] array, int[] revenue) {
            this.optimalCuts = array;
            this.optimalRevenue = revenue;
        }

        public int[] getOptimalCuts() {
            return optimalCuts;
        }

        public int[] getOptimalRevenue() {
            return optimalRevenue;
        }

        public void drawLines(int length) {
            for (int i = 0; i < length; i++) {
                System.out.print("-");
            }
            System.out.println();
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            drawLines(20);
            sb.append("p[i]").append("|");
            Arrays.stream(optimalRevenue).forEach(s -> sb.append(" ").append(s));
            sb.append(System.lineSeparator())
                    .append("s[i]").append("|");
            Arrays.stream(optimalCuts).forEach(s -> sb.append(" ").append(s));
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int rodLength = Integer.parseInt(sc.nextLine());
        int[] price = new int[]{1, 5, 8, 9, 10, 17, 17, 20, 24, 30};
//        int[] memo = new int[rodLength + 1];
//        int optimalRevenueRecursive = recursiveCutRod(rodLength, price);
//        System.out.printf("For rod with lenght: %s, optimal revenue is: %s", rodLength, optimalRevenueRecursive);
//        int optimalRevenueStandard = bottomUpCutRod(rodLength, price);
//        System.out.printf("For rod with lenght: %s, optimal revenue is: %s", rodLength, optimalRevenueStandard);
//
//        int optimalRevenue = memoRecursiveCutRod(rodLength, price, memo);
//        System.out.printf("For rod with lenght: %s, optimal revenue is: %s", rodLength, optimalRevenue);
        System.out.print("i   | ");

        for (int i = 0; i <= rodLength; i++) {
            System.out.printf("%s ", i);
        }
        System.out.println();
        Pair result = extededBottomUpCutRod(rodLength, price);
        System.out.println(result);
//        int optimalRevenue = (int) result[0];
//        System.out.printf("Optimal revenue: %s", optimalRevenue);
//        Arrays.stream(result[1]).forEach(System.out::println);
    }

    public static int memoRecursiveCutRod(int rodLength, int[] price, int[] memo) {
        int revenue = 0;

        if (memo[rodLength] > 0) {
            return memo[rodLength];
        }
        if (rodLength == 0) {
            return revenue;
        } else {
            revenue = Integer.MIN_VALUE;
            for (int i = 1; i <= rodLength; i++) {
                int cutRod = price[i - 1] + memoRecursiveCutRod(rodLength - i, price, memo);
                revenue = Math.max(revenue, cutRod);
            }

            memo[rodLength] = revenue;
        }

        return revenue;
    }

    public static int recursiveCutRod(int rodLength, int[] price) {
        if (rodLength == 0) {
            return 0;
        }
        int revenue = Integer.MIN_VALUE;

        for (int i = 1; i <= rodLength; i++) {
            int cutRod = price[i - 1] + recursiveCutRod(rodLength - i, price);

            revenue = Math.max(revenue, cutRod);
        }

        return revenue;
    }

    public static int bottomUpCutRod(int rodLength, int[] price) {
        int[] memo = new int[rodLength + 1];
        memo[0] = 0;

        for (int j = 1; j <= rodLength; j++) {
            int revenue = Integer.MIN_VALUE;
            for (int i = 1; i <= j; i++) {
                revenue = Math.max(revenue, price[i - 1] + memo[j - i]);
            }
            memo[j] = revenue;
        }
        return memo[rodLength];
    }

    public static Pair extededBottomUpCutRod(int rodLength, int[] price) {
        int[] profit = new int[rodLength + 1];
        int[] optimalCuts = new int[rodLength + 1];

        for (int rodPeace = 1; rodPeace <= rodLength; rodPeace++) {
            int optimalRevenue = Integer.MIN_VALUE;

            for (int peaceOfTheInitialPeace = 1; peaceOfTheInitialPeace <= rodPeace; peaceOfTheInitialPeace++) {
                int currentRevenue = price[peaceOfTheInitialPeace - 1] + profit[rodPeace - peaceOfTheInitialPeace];

                if (currentRevenue > optimalRevenue) {
                    optimalRevenue = currentRevenue;
                    optimalCuts[rodPeace] = peaceOfTheInitialPeace;
                }
                profit[rodPeace] = optimalRevenue;
            }
        }
//        solution.put("optimal-revenue", profit);
//        solution.put("optimal-cutted-rods", optimalCuts);

        return new Pair(optimalCuts, profit);
    }

}
