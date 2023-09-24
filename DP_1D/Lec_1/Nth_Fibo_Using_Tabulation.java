package DP_1D.Lec_1;

import java.util.Scanner;

public class Nth_Fibo_Using_Tabulation {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the value of n : ");
        int n = sc.nextInt();

        int ans = fibo (n);

        System.out.println(ans);

    }

    public static int fibo (int n) {

        // Step-1
        int dp[] = new int[n+1];

        // Step-2 : Initialize dp by analysing base case.
        dp[1] = 1;
        dp[0] = 0;

        // Step-3
        for (int i = 2 ; i <= n ; i++)
            dp[i] = dp[i-1] + dp[i-2];

        // If you are unable to get that what we need to return in tabulation then -->
        // see the arguments in the recursive + memo soln and put them in the dp. Since , we had passed 'n' in the recursive soln i.e. dp[n]
        return dp[n];
    }
}
