package Buy_and_Sell_Stocks.Best_Time_To_Buy_and_Sell_Stock_III;

/*

You are given an array prices where prices[i] is the price of a given stock on the ith day.

Find the maximum profit you can achieve. You may complete at most two transactions.

Note: You may not engage in multiple transactions simultaneously (i.e., you must sell the stock before you buy again).



Example 1:

Input: prices = [3,3,5,0,0,3,1,4]
Output: 6
Explanation: Buy on day 4 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.
Then buy on day 7 (price = 1) and sell on day 8 (price = 4), profit = 4-1 = 3.

Example 2:

Input: prices = [1,2,3,4,5]
Output: 4
Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5-1 = 4.
Note that you cannot buy on day 1, buy on day 2 and sell them later, as you are engaging multiple transactions at the same time. You must sell before buying again.

Example 3:

Input: prices = [7,6,4,3,1]
Output: 0
Explanation: In this case, no transaction is done, i.e. max profit = 0.


Constraints:

1 <= prices.length <= 105
0 <= prices[i] <= 105

 */

/*

in the previous problem we had done this thing :-

if (buy)
    buyKaro = -profit[i] + fn (index + 1 , 0)
    skipKaro = 0 + fn (index + 1 , 1)

    profit = max (buyKaro , skipKaro)

else
    sellKaro = profit[i] + fn (index + 1 , 1)
    skipKaro = 0 + fn (index + 1 , 0)

    profit = max (sellKaro , skipKaro)


---------------> So, in the Buy and Sell Stock II we are allowed to buy and sell infinite stocks and here in the part III
                 we are allowed to do two transactions only. i.e. we will use the previous approach but we will add a new variable i.e.
                 limit <-------- tells that how many transactions are pending till now.

                                      --------------- max value of limit can be '2'
                                      |
                                      V
                 fun (index , buy , limit) {

                      // old base case that we had used in part II also.
                      if (index == n)
                         return 0;

                      // ---> new base case
                      if (limit == 0)
                         return 0;

                 }


                 1 Transaction means jab main ek Buy and then uske baad ek sell kar duu then it will be one transaction.
                 And after 1 transaction we will decreament the limit.

                 fun (index , buy , limit) {

                      if (index == n)
                         return 0;


                      if (limit == 0)
                         return 0;

                      if (buy)
                            buyKaro = -profit[i] + fn (index + 1 , 0 , limit) <----- Kya buy karne se limit kam hogyi ? -> No
                            skipKaro = 0 + fn (index + 1 , 1 , limit)

                            profit = max (buyKaro , skipKaro)

                      else
                            sellKaro = profit[i] + fn (index + 1 , 1 , limit - 1) <---- Sell kar diya i.e. 1 transaction completed.
                            skipKaro = 0 + fn (index + 1 , 0 , limit)

                            profit = max (sellKaro , skipKaro)

                 }

 */

import java.util.Arrays;

public class buyAndSell_III {

    public static void main(String[] args) {

        int []prices = {3,3,5,0,0,3,1,4};

        // ---------------- Recursion ---------------
        int ans1 = solve_recur (prices , 0 , 1 , 2);
        System.out.println(ans1);


        // ---------------- Memoization ---------------
        int n = prices.length;
        int [][][]dp = new int[n][2][3];

        for (int [][]arr : dp) {

            for (int []child : arr) {

                Arrays.fill (child , -1);
            }

        }

        int ans2 = solve_memo (prices , 0 , 1 , 2 , dp);
        System.out.println(ans2);


        // ---------------- Tabulation ---------------
        int ans3 = solve_tabu (prices);
        System.out.println(ans3);

        // --------------- Space Optimisation ----------
        int ans4 = solve_so (prices);
        System.out.println(ans4);


    }

    // ########################### Recursion ########################
    public static int solve_recur (int []prices , int index , int buy , int limit) {

        // Base Case
        if (index == prices.length)
            return 0;

        if (limit == 0)
            return 0;

        // ------> If buy allowed
        int profit = 0;

        if (buy == 1) {

            int buyKaro = -prices[index] + solve_recur (prices , index + 1 , 0 , limit);
            int skipKaro =  solve_recur (prices , index + 1 , 1 , limit);

            profit = Math.max (buyKaro , skipKaro);

        } else { // ---------> If buy not allowed i.e. we need to sell

            int sellKaro = prices[index] + solve_recur (prices , index + 1 , 1 , limit - 1);
            int skipKaro =  solve_recur (prices , index + 1 , 0 , limit);

            profit = Math.max (sellKaro , skipKaro);
        }

        return profit;
    }



    // ########################### Memoization  ########################
    /*

       In the recursive call three parameters are changing i.e. index , buy and limit.

     */

    public static int solve_memo (int []prices , int index , int buy , int limit , int [][][]dp) {

        // Base Case
        if (index == prices.length)
            return 0;

        if (limit == 0)
            return 0;

        // Step-3 :
        if (dp[index][buy][limit] != -1) {

            return dp[index][buy][limit];
        }


        // ------> If buy allowed
        int profit = 0;
        if (buy == 1) {

            int buyKaro = -prices[index] + solve_memo (prices , index + 1 , 0 , limit , dp);
            int skipKaro =  solve_memo (prices , index + 1 , 1 , limit , dp);

            profit = Math.max (buyKaro , skipKaro);

        } else { // ---------> If buy not allowed i.e. we need to sell

            int sellKaro = prices[index] + solve_memo (prices , index + 1 , 1 , limit - 1 , dp);
            int skipKaro =  solve_memo (prices , index + 1 , 0 , limit , dp);

            profit = Math.max (sellKaro , skipKaro);
        }

        // Step-2 :
        return dp[index][buy][limit] = profit;
    }



    // ########################### Tabulation ########################

    public static int solve_tabu (int []prices) {

        int n = prices.length;

        // Step-1 :
        int [][][]dp = new int[n+1][2][3];

        // Step-2 :Base Case Analysis
        // Already done since , index == n is already filled with '0'  and when limit == 0 then fill dp with 0 that we had done already.

        for (int index = n-1 ; index >= 0 ; index--) {

            for (int buy = 0 ; buy <= 1 ; buy++) {

                // since limit == 0 is already handled i.e. we are starting from limit = 1
                for (int limit = 1 ; limit <= 2 ; limit++) {

                    int profit = 0;
                    if (buy == 1) {

                        int buyKaro = -prices[index] + dp[index + 1][0][limit];
                        int skipKaro =  dp[index + 1][1][limit];

                        profit = Math.max (buyKaro , skipKaro);

                    } else { // ---------> If buy not allowed i.e. we need to sell

                        int sellKaro = prices[index] + dp[index + 1][1][limit - 1];
                        int skipKaro =  dp[index + 1][0][limit];

                        profit = Math.max (sellKaro , skipKaro);
                    }

                    dp[index][buy][limit] = profit;
                }
            }
        }

        // Jab bhi lagye kya return karna hain toh Recursion + Memo waale main jo parameters send kiye the from the main function woi yaha par chaap do.
        return dp[0][1][2];

    }



    // ########################### Space Optimization ########################

    /*

             dp[index][buy] depends on profit and profit depends on sellKaro/buyKaro , skipKaro
             and sellKaro/buyKaro , skipKaro  depends on dp[index+1][1]  and dp[index+1][0]



             dp[index][buy] -------------------> dp[index+1][1][limit-1]
                           \
                             \
                               \
                                 \
                                  dp[index+1][0][limit]


             Where : index is moving from (n-1) to 0

             ------------------------------
             |                            |  0
             ------------------------------
             |                            |
             ------------------------------
             |                            |
             ------------------------------
             |                            |
             ------------------------------
             |                            |
             ------------------------------
             |                            |
             ------------------------------
             |                            |
             ------------------------------
             |                            | (n-1) <---------- currRow
             ------------------------------
             |                            |  (n)  <---------- nextRow
             ------------------------------


             So, (n-1)th row kii value nikaalne ke liye main nth row par dependent huun.

             And agli iteration main :  currRow goes to (n-2)  and nextRow = currRow



     */

    // TC : O(n)
    // SC : O(1)  <------- because we are only creating two rows containing only 2 elements each i.e. 4 elements
    public static int solve_so (int []prices) {

        int n = prices.length;

        // Step-1 :

        // Both the rows contains only two blocks i.e. [2] for buy and two blocks for three blocks for limit so, that we can use the 2nd block also.
        int [][]currRow = new int[2][3];
        int [][]nextRow = new int[2][3];


        for (int index = n-1 ; index >= 0 ; index--) {

            for (int buy = 0 ; buy <= 1 ; buy++) {

                for (int limit = 1 ; limit <= 2 ; limit++) {

                    int profit = 0;
                    if (buy == 1) {

                        int buyKaro = -prices[index] + nextRow[0][limit];
                        int skipKaro =  nextRow[1][limit];

                        profit = Math.max (buyKaro , skipKaro);

                    } else { // ---------> If buy not allowed i.e. we need to sell

                        int sellKaro = prices[index] + nextRow[1][limit - 1];
                        int skipKaro =  nextRow[0][limit];

                        profit = Math.max (sellKaro , skipKaro);
                    }

                    currRow[buy][limit] = profit;
                }
            }

            nextRow = currRow;
        }

        return nextRow[1][2];

    }
}
