package DP_1D.Lec_2.Min_Cost_Climbing_Stairs_Using_DP_Approach;

import java.util.Scanner;

public class MinimumCost_By_Memoization_1 {

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

         // Step-1
         int dp[] = new int[n+1];

         for (int i = 0 ; i <= n ; i++)
             dp[i] = -1;

         int ans = Math.min (solveByMemo (cost , n-1 , dp) , solveByMemo (cost , n-2 , dp));

         return ans;
    }

    // TC : O(n)
    // SC : O(n) + O(n) ~ O(n)
    //       ^      ^
    //       |      |
    //      dp     stack
    public static int solveByMemo (int cost[] , int n , int dp[]) {

        // Base Case
        if (n == 0)
            return cost[0];

        if (n == 1)
            return cost[1];

        // Step-3 :
        // ans pehle se store hain.
        if (dp[n] != -1)
            return dp[n];

        // Step-2
        dp[n] = Math.min (solveByMemo (cost , n-1 , dp) , solveByMemo (cost , n-2 , dp)) + cost[n];

        return dp[n];
    }
}
