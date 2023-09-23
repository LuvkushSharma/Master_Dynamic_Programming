package DP_2D.LIS;

/*

Given an integer array nums, return the length of the longest strictly increasing
subsequence.

Example 1:

Input: nums = [10,9,2,5,3,7,101,18]
Output: 4
Explanation: The longest increasing subsequence is [2,3,7,101], therefore the length is 4.

Example 2:

Input: nums = [0,1,0,3,2,3]
Output: 4

Example 3:

Input: nums = [7,7,7,7,7,7,7]
Output: 1


Constraints:

1 <= nums.length <= 2500
-104 <= nums[i] <= 104



 */

/*

    nums = [5  , 8  , 3  , 7  , 9  , 1]

    Increasing subsequences :-

    [5 , 8 , 9]
    [8 , 9]
    [3 , 7 , 9]
    [7 , 9]
    [9]
    [1]


    So, basic approach is that we have two choices if we ask each number in the array : Include or exclude

                     i
                    [5  , 8  , 3  , 7  , 9  , 1]
                   /  \
      Inc        /      \  Exc
               /          \
[5,8,3,7,9,1] , {5}          [8,3,7,9,1]
   i                         i

   But before including any number we should check whether it is bigger than the last element that we had selected.
   i.e. in this case it was 5 so , we can select 8

                                                     i
                                                    [5  , 8  , 3  , 7  , 9  , 1]
                                                   /  \
                                      Inc        /      \  Exc
                                               /          \
                                [5,8,3,7,9,1] , {5}          [8,3,7,9,1]
                                   i                         i
                               /     \
                             /         \
                 [5,8,3,7,9,1] {5,8}   [3,7,9,1]
                      i                 i
                      /
                     /
                    ..... at the end arr ends then update the length (len)
                          So, in every base case we updates len.


                What we are doing is adding the included element in the list but it will take a lot of extra space.
                But , we are storing the last included element in the list. And we are using the last element only
                for comparing the curr_element i.e. we are using the last element of the list for checking whether the
                curr_element is compatible enough to be included i.e. it should be greater than the last element of the array.

                In short , we can do this recursion using two indexes i.e. one is
                'i' and another one will point to the last included element.
 */

import java.util.Arrays;

public class Longest_Increasing_Subsequence {

    public static void main(String[] args) {

        int nums[] = {10,9,2,5,3,7,101,18};
        int n = nums.length;

        // -------------- Recursion ---------------
        int ans = solve_recur(n , nums , 0 , -1);
        System.out.println(ans);

        // -------------- Memoization ---------------
        // curr_idx goes from 0 to n-1
        // prev_idx goes from -1 to n-1 ----> i.e. n
        int dp[][] = new int[n][n+1];

        for (int arr[] : dp) {

            Arrays.fill (arr , -1);
        }
        int ans1 = solve_memo(n , nums , 0 , -1 , dp);
        System.out.println(ans1);

        // --------------- Tabulation ------------------
        int ans2 = solve_tab(n , nums);
        System.out.println(ans2);

        // --------------- Space Optimization -------------
        int ans3 = solve_so (n , nums);
        System.out.println(ans3);

    }


    // -------------------- Recursive Soln ---------------------

    public static int solve_recur (int n , int nums[] , int curr_idx , int prev_idx) {

        // Base Case
        if (curr_idx == n) {
            return 0;
        }

        // Agar mera pehla element hain toh main include karunga yaa fir
        // curr element pichle included element se bada hain toh main included karunga.
        int take = 0;
        if (prev_idx == -1 || nums[curr_idx] > nums[prev_idx]) {

            take = 1 + solve_recur (n , nums , curr_idx + 1 , curr_idx);

        }

        // Excluded
        int notTake = 0 + solve_recur (n , nums , curr_idx + 1 , prev_idx);

        return Math.max (take , notTake);
    }


    // ------------------------- Memoization Soln -----------------------
    // Since , two states are changing i.e. curr and prev i.e. we had applied 2D DP

    // TC : O(n^2)
    // SC : O(n^2)

    public static int solve_memo (int n , int nums[] , int curr_idx , int prev_idx , int dp[][]) {

        // Base Case
        if (curr_idx == n) {
            return 0;
        }

        if (dp[curr_idx][prev_idx + 1] != -1) {

            return dp[curr_idx][prev_idx + 1];
        }

        // Included
        int take = 0;
        if (prev_idx == -1 || nums[curr_idx] > nums[prev_idx]) {

            take = 1 + solve_memo (n , nums , curr_idx + 1 , curr_idx , dp);

        }

        // Excluded
        int notTake = 0 + solve_memo (n , nums , curr_idx + 1 , prev_idx , dp);

        // Since , prev starts from -1 i.e. in that case we will get an error index out of bound i.e,
        // put idx as prev_idx + 1

        return dp[curr_idx][prev_idx + 1] = Math.max (take , notTake);
    }

    // ---------------------- Tabulation -----------------------

    // ----- In top down -------
    // curr goes from 0 to n-1 and
    // prev goes from -1 to n-1

    // So, invert in case of Bottom Up
    public static int solve_tab (int n , int nums[]) {

        int dp[][] = new int[n+1][n+1];

        for (int curr = n-1 ; curr >= 0 ; curr--) {

            for (int prev = curr-1;  prev >= -1 ; prev--) {

                // Included
                int take = 0;

                if (prev == -1 || nums[curr] > nums[prev]) {

                    take = 1 + dp[curr + 1][curr + 1];

                }

                // Excluded
                int notTake = 0 + dp[curr + 1][prev + 1];

                // Since , prev starts from -1 i.e. in that case we will get an error index out of bound i.e,
                // put idx as prev_idx + 1

                dp[curr][prev + 1] = Math.max (take , notTake);
            }
        }

        return dp[0][0];
    }

    // -------------------------- Space Optimization ----------------------------

    /*

             dp[curr][prev+1]  -----------------> dp[curr+1][curr+1]
                               \
                                 \
                                   \
                                    dp[curr+1][prev+1]


             dp[curr][prev+1]  depends only on the next row i.e. we donot need the whole dp instead make two 1D arrays


     */

    // TC : O(n^2)
    // SC : O(n)
    public static int solve_so (int n , int nums[]) {

        int currRow[] = new int[n+1];
        int nextRow[] = new int[n+1];

        for (int curr = n-1 ; curr  >= 0 ; curr --) {

            for (int prev = curr -1;  prev >= -1 ; prev--) {

                // Included
                int take = 0;

                if (prev == -1 || nums[curr] > nums[prev]) {

                    take = 1 + nextRow[curr  + 1];

                }

                // Excluded
                int notTake = nextRow[prev + 1];

                currRow[prev + 1] = Math.max (take , notTake);
            }

            nextRow = currRow; // Moving next upwards
        }

        return nextRow[0];
    }

    /*

                So, we had Decreased the SC but we also want the TC : O(nlogn) ----> for that we will now use
                DP with Binary Search.

     */

}
