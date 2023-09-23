package DP_2D.Reducing_Dishes;

/*

A chef has collected data on the satisfaction level of his n dishes. Chef can cook any dish in 1 unit of time.

Like-time coefficient of a dish is defined as the time taken to cook that dish including previous dishes multiplied by its satisfaction level i.e. time[i] * satisfaction[i].

Return the maximum sum of like-time coefficient that the chef can obtain after dishes preparation.

Dishes can be prepared in any order and the chef can discard some dishes to get this maximum value.



Example 1:

Input: satisfaction = [-1,-8,0,5,-9]
Output: 14

--------> Neglecting '-8' and '-9' to get the maximum like-time coefficient

Explanation: After Removing the second and last dish, the maximum total like-time coefficient will be equal to (-1*1 + 0*2 + 5*3 = 14).
Each dish is prepared in one unit of time.

Example 2:

Input: satisfaction = [4,3,2]
Output: 20

-----------------> First of all sort the dishes
-----------------> First dish takes 1 unit of time and chef can start making the second dish from 2nd unit. Similarly , after finishing
                   1 more unit of time i.e. when the second dish is prepared then only from 3unit of time 3rd dish can be prepared.
                   i.e. 2*1 , 3*2 , 4*3

Explanation: Dishes can be prepared in any order, (2*1 + 3*2 + 4*3 = 20)

Example 3:

Input: satisfaction = [-1,-4,-5]
Output: 0
Explanation: People do not like the dishes. No dish is prepared.


Constraints:

n == satisfaction.length
1 <= n <= 500
-1000 <= satisfaction[i] <= 1000


 */

/*

Ex : {4 , 0 , -1}


Step-1 : Sort the array : {-1 , 0 , 4}

Step-2 : Since , we have a choice of neglecting a dish i.e. 0-1 knapsack i.e. select to make a dish or donot select to make a dish.


                                                   i
                                                 {-1 , 0 , 4}
                                                 /  \
                                        INC     /    \  EXC
                                               /      \
                                        i     /        \
                                    {-1,0,4} , -1
                                        / \
                               INC     /   \  EXC
                                      /     \
                                i    /       \
                          {-1,0,4} , -1
                               / \
                     INC      /   \    EXC
                             /     \
                        i   /       \           i
                {-1,0,4} , 11       {-1 , 0 , 4} , -1
                |                               |
                | (-1 + 4*3) = 11               |
                |                               |
                v                               V
        Base Case Hits.                      Base Case Hits.



 */

import java.util.Arrays;

public class reducingDishes {

    public static void main(String[] args) {

        int []satisfaction = {-1,-8,0,5,-9};

        // Step-1 : Sort the array
        Arrays.sort (satisfaction);

        // --------------------- Recursion ---------------------
        int ans1 = solve_recur (satisfaction , 0 , 0);
        System.out.println(ans1);


        // --------------------- Memoization -----------------
        int n = satisfaction.length;
        int [][]dp = new int[n+1][n+1];

        for (int arr[] : dp) {

            Arrays.fill (arr , -1);
        }

        int ans2 = solve_memo (satisfaction , 0 , 0 , dp);
        System.out.println(ans2);


        // ---------------------- Tabulation ------------------
        int ans3 = solve_tabu (satisfaction);
        System.out.println(ans3);

        // -------------------- Space optimization -------------
        int ans4 =  solve_so (satisfaction);
        System.out.println(ans4);


    }


    // #################### Recursion ################

    // time : at which we are start making the dish
    public static int solve_recur (int [] satisfaction , int idx , int time) {

        // Base Case
        if (idx == satisfaction.length) {

            return 0;
        }

        // Ek case hamne solve karke de diya aagye kaa recursion solve karke de dega.
        int include = satisfaction[idx] * (time + 1) + solve_recur (satisfaction , idx + 1 , time + 1);

        // Since , maine koi dish nhi banaai isliye time remains same
        int exclude = 0 + solve_recur (satisfaction , idx + 1 , time);

        return Math.max (include , exclude);

    }

    // #################### Memoization ################
    /*

       By seeing the recursive call we get to know that two parameters are changing i.e.
       idx and time i.e. 2D DP

     */
    public static int solve_memo (int [] satisfaction , int idx , int time , int[][]dp) {

        // Base Case
        if (idx == satisfaction.length) {

            return 0;
        }

        if (dp[idx][time] != -1) {

            return dp[idx][time];
        }

        int include = satisfaction[idx] * (time + 1) + solve_memo (satisfaction , idx + 1 , time + 1 , dp);

        int exclude = solve_memo (satisfaction , idx + 1 , time , dp);

        return dp[idx][time] = Math.max (include , exclude);

    }

    // #################### Tabulation ################
    /*
          In top-down : idx goes from 0 to n
                        time goes from 0 to n

          In bottom-up : reverse it.


     */
    public static int solve_tabu (int [] satisfaction) {

        int n = satisfaction.length;

        // Step-1 :
        int [][]dp = new int[n+1][n+1];

        // Since i had already initialized the index with '0' i.e. no need to further re-declare.

        // Since idx = n ke liye already '0' filled hain i.e. start from n-1
        for (int idx = n-1 ; idx >= 0 ; idx--) {

            for (int time = idx ; time >= 0 ; time--) {

                int include = satisfaction[idx] * (time + 1) + dp[idx + 1][time + 1];
                int exclude = dp[idx + 1][time];

                dp[idx][time] = Math.max (include , exclude);
            }
        }

        return dp[0][0];
    }

    // #################### Space Optimization ################
    /*
          dp[idx][time] ----------------> dp[idx+1][time] (Next row , curr colm)
                       \
                        \
                         \
                          \
                          dp[idx + 1][time + 1]  (Next row , next colm)


                  dp[idx + 1][...] means next row

     */
    public static int solve_so (int [] satisfaction) {

        int n = satisfaction.length;

        // Step-1 :
        int []currRow = new int[n+1];
        int []nextRow = new int[n+1];


        for (int idx = n-1 ; idx >= 0 ; idx--) {

            for (int time = idx ; time >= 0 ; time--) {

                int include = satisfaction[idx] * (time + 1) + nextRow[time + 1];
                int exclude = nextRow[time];

                currRow[time] = Math.max (include , exclude);
            }

            nextRow = currRow.clone();
        }

        return nextRow[0];
    }

}
