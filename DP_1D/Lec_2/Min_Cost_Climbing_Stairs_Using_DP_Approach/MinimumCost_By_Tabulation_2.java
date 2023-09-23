package DP_1D.Lec_2.Min_Cost_Climbing_Stairs_Using_DP_Approach;

import java.util.Scanner;

public class MinimumCost_By_Tabulation_2 {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        int cost[] = new int[n];

        for (int i = 0 ; i < n ; i++) {

            cost[i] = sc.nextInt();
        }

        int ans = minCostClimbingStairs (cost);

        System.out.println(ans);
    }

    public static int minCostClimbingStairs (int cost[]) {

        int n = cost.length;

        return solveByTabu(cost , n);
    }

    // TC : O(n)
    // SC : O(n)
    public static int solveByTabu (int cost[] , int n) {

        // Step-1
        int dp[] = new int[n+1];

        // Step-2 : Base Case ke accordingly values fill kardo
        dp[0] = cost [0];
        dp[1] = cost [1];

        // Step-3 : Bache hue cases solve karo
        for (int i = 2 ; i < n ; i++) {

            dp[i] = cost[i] + Math.min (dp[i-1] , dp[i-2]);
        }

        return Math.min (dp[n-1] , dp[n-2]);
    }
}
