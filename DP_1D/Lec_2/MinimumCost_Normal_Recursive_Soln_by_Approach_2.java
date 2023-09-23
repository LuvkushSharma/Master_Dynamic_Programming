package DP_1D.Lec_2;

/*

You are given an integer array cost where cost[i] is the cost of ith step on a staircase. Once you pay the cost,
you can either climb one or two steps.

You can either start from the step with index 0, or the step with index 1.

Return the minimum cost to reach the top of the floor.



Example 1:

Input: cost = [10,15,20]
Output: 15
Explanation: You will start at index 1.
- Pay 15 and climb two steps to reach the top.
The total cost is 15.

Example 2:

Input: cost = [1,100,1,1,1,100,1,1,100,1]
Output: 6
Explanation: You will start at index 0.
- Pay 1 and climb two steps to reach index 2.
- Pay 1 and climb two steps to reach index 4.
- Pay 1 and climb two steps to reach index 6.
- Pay 1 and climb one step to reach index 7.
- Pay 1 and climb two steps to reach index 9.
- Pay 1 and climb one step to reach the top.
The total cost is 6.


Constraints:

2 <= cost.length <= 1000
0 <= cost[i] <= 999


 */

/*

Hame , (n-1)th stair tak jaane kii costs dii hain but jab ham nth stair par pahauch jaayengye then uski koi bhi cost nhi hogyi.

 */

// Recursive relation : f(k) = min (f(k-1) , f(k-2)) + cost[k]

import java.util.Scanner;

public class MinimumCost_Normal_Recursive_Soln_by_Approach_2 {

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

        int ans = Math.min (solve (cost , n-1) , solve (cost , n-2));
        return ans;
    }

    public static int solve (int cost[] , int n) {

        // Base Case
        if (n == 0)
            return cost[0];

        if (n == 1)
            return cost[1];

        int ans = Math.min (solve(cost , n-1) , solve (cost , n-2)) + cost[n];

        return ans;
    }

}
