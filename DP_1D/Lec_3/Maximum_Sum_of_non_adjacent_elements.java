package DP_1D.Lec_3;

/*

Question Link : https://www.codingninjas.com/studio/problems/maximum-sum-of-non-adjacent-elements_843261

You are given an array/list of 'N' integers. You are supposed to return the maximum sum of the subsequence with the constraint that
no two elements are adjacent in the given array/list.

Note:
A subsequence of an array/list is obtained by deleting some number of elements (can be zero) from the array/list, leaving the
remaining elements in their original order.

Sample Input 1:
2 <------------- testcase
3
1 2 4
4
2 1 4 9
Sample Output 1:
5
11

Explanation To Sample Output 1:
In test case 1, the sum of 'ARR[0]' & 'ARR[2]' is 5 which is greater than 'ARR[1]' which is 2 so the answer is 5.

In test case 2, the sum of 'ARR[0]' and 'ARR[2]' is 6, the sum of 'ARR[1]' and 'ARR[3]' is 10, and the sum of 'ARR[0]' and 'ARR[3]'
is 11. So if we take the sum of 'ARR[0]' and 'ARR[3]', it will give the maximum sum of sequence in which no elements are adjacent
in the given array/list.

Sample Input 2:
2
5
1 2 3 5 4
9
1 2 3 1 3 5 8 1 9

Sample Output 2:
8
24

Explanation To Sample Output 2:
In test case 1, out of all the possibilities, if we take the sum of 'ARR[0]', 'ARR[2]' and 'ARR[4]', i.e. 8, it will give the
maximum sum of sequence in which no elements are adjacent in the given array/list.

In test case 2, out of all the possibilities, if we take the sum of 'ARR[0]', 'ARR[2]', 'ARR[4]', 'ARR[6]' and 'ARR[8]', i.e. 24 so,
it will give the maximum sum of sequence in which no elements are adjacent in the given array/list.

 */


/*


--------------------------------------   Approach -----------------------------------

So, we have two choices only whether to take or not to take.

Note : We cannot select two adjacent elements

                                         i
                                       { 9  ,  9  ,  8  ,  2}
                                       /  \
                               INC    /    \ EXC
                       (i+2)         /      \  (i+1)
                                    /        \
                      { 9,9,8,2} , 9         {9,9,8,2} , 0
                            i                   i
            INC          /    \  EXC
                        /      \
        (i+2)          /        \ (i+1)
                      /          \
          {9,9,8,2} , 17    {9,9,8,2} , 9
                   i               i
               |             INC /   \ EXC (i+1)
               |                /     \
               V         {9,9,8,2},11  {9,9,8,2},9
          Base Condition          i             i
            matches               |             |
                                  |             |
                                  V             V
                               Base Case     Base Case

        So, in case of Include : (i+2) because we are not allowed to select adjacent element
       and, in case of Exclude : (i+1) because we didn't select the 'ith' element i.e. we can select the (i+1)th element.


  Similarly, we can start from the end also, where instead of (i+2) we do (i-2) ,...

 */

import java.util.Arrays;

public class Maximum_Sum_of_non_adjacent_elements {

    public static void main(String[] args) {

        int []nums = {1 , 2 , 3 , 1 , 3 , 5 , 8 , 1 , 9};
        int n = nums.length;

        // -------------- Recursion -------------
        // So, i am trying to find the answer by traversing from right to left. i.e. i = n-1
        int ans = solve_recur(nums , n-1);

        // -------------- Memoization -------------
        int []dp = new int[n];
        Arrays.fill (dp , -1);
        int ans1 = solve_memo (nums , n-1 , dp);

        // -------------- Tabulation -------------
        int ans2 = solve_tabu (nums);

        // --------------- Space Optimization -----------
        int ans3 = solve_so (nums);

        System.out.println(ans3);
    }

    // ################## Recursion ##################
    public static int solve_recur (int []nums , int i) {

        // Base Case
        if (i < 0) {

            return 0;
        }

        // If  only one element remains in the array
        if (i == 0) {

            return nums[0];
        }

        int include = nums[i] + solve_recur (nums , i-2);
        int exclude = 0 + solve_recur (nums , i-1);

        return Math.max (include , exclude);
    }

    // ################## Memoization ##################
    // TC : O(N)
    // SC : O(N) + O(N)
    //              |
    //              ------> Stack space
    public static int solve_memo (int []nums , int i , int []dp) {

        // Base Case
        if (i < 0) {

            return 0;
        }

        // If  only one element remains in the array
        if (i == 0) {

            return nums[0];
        }

        // Step-3 :
        if (dp[i] != -1) {

            return dp[i];
        }

        int include = nums[i] + solve_memo (nums , i-2 , dp);
        int exclude = solve_memo (nums , i-1 , dp);

        // Step-2 :
        return dp[i] = Math.max (include , exclude);
    }


    // ################## Tabulation ##################
    // TC : O(N)
    // SC : O(N)

    public static int solve_tabu (int []nums) {

        int n = nums.length;

        // Step-1 :
        int []dp = new int[n];

        // Step-2 : Ananlyzing the base case and fill the dp accordingly.
        dp[0] = nums[0];

        dp[1] = Math.max (nums[1] , nums[0]);

        for (int i = 2 ; i < n ; i++) {

            int include = nums[i] + dp[i-2];
            int exclude = 0 + dp[i-1];

            dp[i] = Math.max (include , exclude);
        }

        return dp[n-1];
    }


    // ################## Space Optimization ##################
    /*

           Step-1 : dp[i] kis par dependent hain : include and exclude
           whereas include and exclude are dependent on dp[i-2] and dp[i-1] respectively.


           dp[i] --------------------> dp[i-2]
                 \
                  \
                   \
                     dp[i-1]

           i.e. if (n < 0) return 0 ----------> i.e. prev2  = 0
                if (n == 0) ---------------> prev2 = nums[0]


                so, jahan par dp[i-2] hain wahan par prev2 laga do and jahan par dp[i-1] hain wahan par prev1 laga do.

     */
    public static int solve_so (int []nums) {

        int n = nums.length;

        // Step-1 :
        int prev2 = 0;
        int prev1 = nums[0];

        for (int i = 1 ; i < n ; i++) {

            int include = nums[i] + prev2;
            int exclude = 0 + prev1;

            int curr = Math.max (include , exclude);

            // Moving prev1 and prev2
            prev2 = prev1;
            prev1 = curr;
        }

        return prev1;
    }
}
