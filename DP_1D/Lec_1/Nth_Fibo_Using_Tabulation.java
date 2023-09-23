package DP_1D.Lec_1;

import java.util.Scanner;

public class Nth_Fibo_Using_Tabulation {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the value of n : ");
        int n = sc.nextInt();

        // Step-1
        int dp[] = new int[n+1];

        // Step-2 : Initialize dp by checking base case
        dp[1] = 1;
        dp[0] = 0;

        int ans = fibo (n , dp);

        System.out.println(ans);

    }

    public static int fibo (int n , int dp[]) {

        // Step-3
        for (int i = 2 ; i <= n ; i++)
            dp[i] = dp[i-1] + dp[i-2];

        return dp[n];
    }
}
