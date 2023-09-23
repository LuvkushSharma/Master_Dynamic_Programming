package DP_1D.Lec_1;

import java.util.Scanner;

public class Nth_Fibo_Using_Memoization{

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Enter the value of n : ");
        int n = sc.nextInt();
        int dp[] = new int[n+1];

        // Step-1 : Initialize dp by -1
        for (int i = 0 ; i <= n ; i++)
            dp[i] = -1;

        int ans = fibo (n , dp);

        System.out.println(ans);
        
    }

    public static int fibo (int n , int dp[]) {

        // Base Case
        if (n == 1 || n == 0)
            return n;

        // Step-3
        if (dp[n] != -1)
            return dp[n];

        // Step-2
        dp[n] = fibo(n-1 , dp) + fibo(n-2 , dp);

        return dp[n];
    }
}
