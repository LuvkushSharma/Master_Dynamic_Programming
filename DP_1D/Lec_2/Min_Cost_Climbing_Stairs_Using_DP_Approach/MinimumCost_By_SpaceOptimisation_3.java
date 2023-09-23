package DP_1D.Lec_2.Min_Cost_Climbing_Stairs_Using_DP_Approach;

import java.util.Scanner;

public class MinimumCost_By_SpaceOptimisation_3 {

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

    // TC : O(n)
    // SC : O(1)
    public static int minCostClimbingStairs (int cost[]) {

        int n = cost.length;

        int ans = solveBySpaceOptimise (cost , n);
        return ans;
    }

    public static int solveBySpaceOptimise (int cost[] , int n) {

        // Step-1
        int prev2 = cost [0];
        int prev1 = cost [1];

        // Step-2
        for (int i = 2 ; i < n ; i++) {

            int curr = cost [i] + Math.min (prev1 , prev2);
            prev2 = prev1;
            prev1 = curr;
        }

        return Math.min (prev1 , prev2);
    }
}
