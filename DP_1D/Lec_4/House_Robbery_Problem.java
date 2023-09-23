package DP_1D.Lec_4;

/*

Question_Link : https://www.codingninjas.com/studio/problems/house-robber_839733

Mr. X is a professional robber planning to rob houses along a street. Each house has a certain amount of money hidden.
All houses along this street are arranged in a circle. That means the first house is the neighbor of the last one.
Meanwhile, adjacent houses have a security system connected, and it will automatically contact the police if two adjacent houses
were broken into on the same night. You are given an array/list of non-negative integers 'ARR' representing the amount of money of
each house. Your task is to return the maximum amount of money Mr. X can rob tonight without alerting the police.

Note:
It is possible for Mr. X to rob the same amount of money by looting two different sets of houses. Just print the maximum possible robbed amount, irrespective of sets of houses robbed.

For Example:
(i) Given the input array arr[] = {2, 3, 2} the output will be 3 because Mr X cannot rob house 1 (money = 2) and then
rob house 3 (money = 2), because they are adjacent houses. So, he'll rob only house 2 (money = 3)

(ii) Given the input array arr[] {1, 2, 3, 1} the = output will be 4 because Mr X rob house 1 (money 1) = and then rob house 3 (money = 3).

(iii) Given the input array arr[] {0} the output = will be because Mr. X has got nothing to rob.

Sample Input 1:
3
1
0
3
2 3 2
4
1 3 2 1

Sample Output 1:
0
3
4

Explanation Of Input 1:
(i) Mr. X has only one house to rob, but with no money.

(ii) Mr. X cannot rob house 1 (money = 2) and then rob house 3 (money = 2), because they are adjacent houses (remember, it’s a circular street). So, he’ll rob only house 2 (money = 3) with a maximum value

(iii) Mr. X will get maximum value when he robs house 2 (money = 3) and then robs house 4 (money = 1) i.e. 4 units of money.

Sample Input 2:
3
5
1 5 1 2 6
3
2 3 5
4
1 3 2 0

Sample Output 2:
11
5
3

 */

/*

This question is similar to the Maximum sum of non-adjacent elements. But , their is a small catch.

For ex : {9  , 8  ,7}

So, according to the previous approach we will take 9 and then 7. Unable to select 8 because we cannot select adjacent elements.
But , It is given in the question that houses are arranged in the cyclic manner i.e. 7 is adjacent to 9. Hence , we cannot directly use
the previous soln.

So, in this case : ans = 8  ---> Because if we do robbery in house no. 0 and house no.2 then security alarm will be fired.

i.e. we only need to handle only one testcase.

i.e. agar mere soln main first element hain then last element nhi hona chaiye and vice-versa.

            --------------------------------
            |   1   |  3    |  2   |   7   |
            --------------------------------
                ^
                |
              Included so, 7 should not be
              included

            --------------------------------
            |   1   |  3    |  2   |   7   |
            --------------------------------
                                       ^
                                       |
                                    Included so, 1 should not be
                                    included

           So, in case-1 i.e. when 1 is selected :
           we only need to solve the below part

           -------------------------
           |   1    |  3    |  2   |   --------> Skipping last element
           -------------------------

           and in case-2 i.e. when 7 is selected :
           we only need to solve the below part

           ------------------------
           |  3    |  2   |   7   |   ---------> Skipping first element
           ------------------------

           and maximum out of both the cases will be our answer.


 */

import java.util.ArrayList;
import java.util.Arrays;

public class House_Robbery_Problem {

    public static void main(String[] args) {

        int []money = {1 , 5 , 1  , 2 , 6};
        // int []money = {2,7,9,3,1};

        int n = money.length;

        // Only one home for robbery
        if (n == 1) {

            System.out.println(money[0]);

        } else {

            ArrayList<Integer> first = new ArrayList<>();
            ArrayList<Integer> second = new ArrayList<>();

            for (int i = 0 ; i < n ; i++) {

                // Means 0th element is selected
                if (i != (n-1)) {

                    first.add (money[i]);
                }

                /// Means 0th element is rejected
                if (i != 0) {

                    second.add (money[i]);
                }
            }

            // ------------ Recursion -------------
            int case1 = solve_recur (first , first.size()-1);
            int case2 = solve_recur (second , second.size()-1);

            int ans1 = Math.max (case1 , case2);
            System.out.println(ans1);

            // ------------ Memoization -------------
            int []dp = new int[n];
            Arrays.fill (dp , -1);

            int case3 = solve_memo (first , first.size()-1 , dp);
            int case4 = solve_memo (second , second.size()-1 , dp);

            int ans2 = Math.max (case3 , case4);
            System.out.println(ans2);

            // ------------ Tabu -------------
            /*
            int case5 = solve_tabu (first);
            int case6 = solve_tabu (second);

            int ans3 = Math.max (case5 , case6);
            System.out.println(ans3);

            // --------------- Space Optimization -----------
            int ans4 = solve_so (money);
            System.out.println(ans4);

             */


        }
    }

    // ################## Recursion ##################
    public static int solve_recur (ArrayList<Integer> money , int i) {

        // Base Case
        if (i < 0) {

            return 0;
        }

        // If only one element remains in the array
        if (i == 0) {

            return money.get(0);
        }

        int include = money.get(i) + solve_recur (money ,i-2);
        int exclude = solve_recur (money ,i-1);

        return Math.max (include , exclude);
    }

    // ################## Memoization ##################

    // TC : O(N)
    // SC : O(N) + O(N)
    //              |
    //              ------> Stack space
    public static int solve_memo (ArrayList<Integer> money , int i , int []dp) {

        // Base Case
        if (i < 0) {

            return 0;
        }

        // If only one element remains in the array
        if (i == 0) {

            return money.get(0);
        }

        if (dp[i] != -1) {

            return dp[i];
        }

        int include = money.get(i) + solve_memo (money ,i-2 , dp);
        int exclude = solve_memo (money ,i-1 , dp);

        // Step-2 :
        return dp[i] = Math.max (include , exclude);
    }


    // ################## Tabulation ##################
    // TC : O(N)
    // SC : O(N)
    /*

    public static int solve_tabu (ArrayList<Integer> money) {

        int n = money.size();

        // Step-1 :
        int []dp = new int[n];

        // Step-2 : Ananlyzing the base case and fill the dp accordingly.
        dp[0] = money.get(0);

        dp[1] = Math.max (money.get(1) , money.get(0));

        for (int i = 2 ; i < n ; i++) {

            int include = money.get(i) + dp[i-2];
            int exclude = dp[i-1];

            dp[i] = Math.max (include , exclude);
        }

        return dp[n-1];
    }


    // ################## Space Optimization ##################

    public static int solve_so (int []money) {

        int n = money.length;

        // Step-1 :
        int prev2 = 0;
        int prev1 = money[0];

        for (int i = 1 ; i < n ; i++) {

            int include = money[i] + prev2;
            int exclude = 0 + prev1;

            int curr = Math.max (include , exclude);

            // Moving prev1 and prev2
            prev2 = prev1;
            prev1 = curr;
        }

        return prev1;
    }

    */

}
