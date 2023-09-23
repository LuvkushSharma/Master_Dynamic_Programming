package Buy_and_Sell_Stocks.Best_Time_To_Buy_and_Sell_Stock_II;

/*

You are given an integer array prices where prices[i] is the price of a given stock on the ith day.

On each day, you may decide to buy and/or sell the stock. You can only hold at most one share of the stock at any time. However, you can buy it then immediately sell it on the same day.

Find and return the maximum profit you can achieve.



Example 1:

Input: prices = [7,1,5,3,6,4]
Output: 7
Explanation: Buy on day 2 (price = 1) and sell on day 3 (price = 5), profit = 5-1 = 4.
Then buy on day 4 (price = 3) and sell on day 5 (price = 6), profit = 6-3 = 3.
Total profit is 4 + 3 = 7.

Example 2:

Input: prices = [1,2,3,4,5]
Output: 4
Explanation: Buy on day 1 (price = 1) and sell on day 5 (price = 5), profit = 5-1 = 4.
Total profit is 4.

Example 3:

Input: prices = [7,6,4,3,1]
Output: 0
Explanation: There is no way to make a positive profit, so we never buy the stock to achieve the maximum profit of 0.


Constraints:

1 <= prices.length <= 3 * 104
0 <= prices[i] <= 104


 */

/*

-----------------------------   Question Explanation ------------------------

Now , in this variant we can buy and sell on the same day. i.e.

Where : B is Buy and S is Sell

prices = {7  ,  1  ,  5   ,  3   ,  6   ,   4}
                B     S      B      S       BS
                |     |      |      |        |
                -------      --------        ---------> Profit : (4-4) => 0
                  |              |
                  v              V
        Profit : (5-1) => 4    Profit : (6-3) => 3

Total profit : 4 + 3 + 0 ==> 7

Condition : Ek baar "Buy" karne ke baad ham "Buy" nhi kar sakte pehle hame uss bought stock ko sell karna padega then only we can buy another stock.

Note : Jitni marzi buy karo and sell karo but two conditions should be fulfilled :
       1. Buy ke baad Buy naa ho
       2. Sell ke baad Sell naa ho.

 */

/*

--------------------------- Intuition ----------------------------------

Here , multiple combinations are possible and out of which we want Max Profit.

So, generate all the combinations and select best answer.

Approach is same as many problems i.e. take , not take  Or  include or not include.


                             1. Buy ke baad Buy naa ho
                             2. Sell ke baad Sell naa ho.

                             So, we will take a variable i.e. int buy and when it is 0 then it means we cannot buy because
                             we had purchased a stock already so, we need to sell it then only we can buy more stocks.

                             i.e. buy ---> 0 ----> not allowed to buy
                                  buy ---> 1 ----> buy allowed.


                                  And , total mere pass three choices hain kisi bhi index par
                                   1. Yaa toh buy karo
                                   2. Yaa toh sell karo
                                   3. Yaa toh ignore karo

                                 Note : Above conditions should be fulfilled.


                                  ️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️️

                                  In case of buy = 1 we have two choices :

                                  buy = 1   ---------------> Buy karo
                                            \
                                              \
                                                \
                                                  \
                                                    Ignore karo

                                  In case of buy = 0 we have two choices :

                                  buy = 0   ---------------> Sell karo
                                            \
                                              \
                                                \
                                                  \
                                                    Ignore karo

                                  ⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️


                      Now , let's say i choose to buy at index = 1 and selling on index = 2


                                  0       1       2       3      4       5
                              -------------------------------------------------
                              |   7   |   1   |   5   |   3   |   6   |   4   |
                              -------------------------------------------------
                                         B        S

                     So, profit += (5 - 1) i.e. (5 + (-1))

                     So, we can say that When we sell then it is added a +ve number. (5)
                         and jab bhi main buy kar raha huun then woh -ve sign ke saath add ho raha hain. i.e. (-1)


                    Hence , jab bhi main Buy karunga toh buy kii price : -ve sign ke saath add ho jaayegyi
                            and in case of sell ---> price will add with a +ve sign.


                --------------------------------->  LOGIC


                                  0       1       2       3      4       5
                              -------------------------------------------------
                              |   7   |   1   |   5   |   3   |   6   |   4   |
                              -------------------------------------------------

                 select = -price[i] + solve (i + 1 , 0) -----> matlab when buy == 1 means we can buy
                 so, when we select to buy then add price[i] with a -ve sign and bacaha hua part recursion akr dega. i.e.
                 + solve(i + 1 , 0) <---- abb buy = 0 bhej diya hain argument main.

                  public static int solve (int index , int buy) {


                          if (buy == 1) {

                             // Buy karo waala part
                             select = -price[i] + solve (i + 1 , 0);

                             // Skip karo waala part
                             skip = 0 + solve (i + 1 , 1);

                             max (select , skip)

                          }  else {

                            // Buy karna allowed nhi hain

                            select = price[i] + solve (i+1 , 1); <----- argument main abb buy = 1 kardia so, that next time ham buy karengye.
                            skip = 0 + solve (i + 1 , 0); <--- i.e. we are still not allowed to buy so, next time also we need to sell the stock.

                            max (select , skip)

                          }

                  }


 */

import java.util.Arrays;

public class buySellStock_II {

    public static void main(String[] args) {

         int []prices = {7  , 1  , 5  , 3 ,  6  , 4};
         int n = prices.length;

         // ----------------- Recursion -------------------
         int ans1 = solve_recur (prices , 0 , 1);
         System.out.println(ans1);


        // ------------------ Memoization -----------------

        // Step-1 :
        int [][]dp = new int[n][2];

        for (int arr[] : dp) {

            Arrays.fill (arr , -1);

        }

        int ans2 = solve_memo (prices , 0 , 1 , dp);
        System.out.println(ans2);



        // ------------------ Tabulation ------------------
        int ans3 = solve_tabu (prices);
        System.out.println(ans3);

        // ------------------- Space Optimization ----------
        int ans4 = solve_so (prices);
        System.out.println(ans4);


    }

    // ################### Recursion ####################
    public static int solve_recur (int []prices , int index , int buy) {

        // Base Case
        if (index == prices.length) {

            return 0;
        }

        // ------> If buy allowed
        int profit = 0;
        if (buy == 1) {

            int buyKaro = -prices[index] + solve_recur (prices , index + 1 , 0);
            int skipKaro =  solve_recur (prices , index + 1 , 1);

            profit = Math.max (buyKaro , skipKaro);

        } else { // ---------> If buy not allowed i.e. we need to sell

            int sellKaro = prices[index] + solve_recur (prices , index + 1 , 1);
            int skipKaro =  0 + solve_recur (prices , index + 1 , 0);

            profit = Math.max (sellKaro , skipKaro);
        }

        return profit;
    }



    // ################### Memoization ####################
    /*
           Since , in the recursive call two parameters are changing i.e. index and buy i.e. 2D DP.

           Rows hamaari at max 'n' tak jaa sakti hain. And
           Column hamaara at max '2' hee hain i.e. colm = 0 or colm = 1


     */
    public static int solve_memo (int []prices , int index , int buy , int [][]dp) {

        // Base Case
        if (index == prices.length) {

            return 0;
        }

        // Step-3 :
        if (dp[index][buy] != -1) {

            return dp[index][buy];
        }


        // ------> If buy allowed
        int profit = 0;
        if (buy == 1) {

            int buyKaro = -prices[index] + solve_memo (prices , index + 1 , 0 , dp);
            int skipKaro =  solve_memo (prices , index + 1 , 1 , dp);

            profit = Math.max (buyKaro , skipKaro);

        } else { // ---------> If buy not allowed i.e. we need to sell

            int sellKaro = prices[index] + solve_memo (prices , index + 1 , 1 , dp);
            int skipKaro =  0 + solve_memo (prices , index + 1 , 0 , dp);

            profit = Math.max (sellKaro , skipKaro);
        }

        // Step-2 :
        return dp[index][buy] = profit;
    }



    // ###################### Tabulation ######################
    /*

         In Top-down : index goes from 0 to n
                       buy goes from 1 to 0

         In Bottom Up : index goes from n-1 to 0
                        buy goes from 0 to 1

     */

    // TC : O(n)
    // SC : O(n)
    public static int solve_tabu (int []prices) {

        int n = prices.length;

        // Step-1 :
        int [][]dp = new int[n+1][2];

        // Step-2 :Base Case Analysis
        // Already done since , index == n is already filled with '0'

        for (int index = n-1 ; index >= 0 ; index--) {

            for (int buy = 0 ; buy <= 1 ; buy++) {

                int profit = 0;
                if (buy == 1) {

                    int buyKaro = -prices[index] + dp[index + 1][0];

                    // Since , we are starting from index = n-1 and here we are doing : dp[index+1] i..e index out of bound error
                    // will come i.e. create dp[n+1[2]
                    int skipKaro =  dp[index + 1][1];

                    profit = Math.max (buyKaro , skipKaro);

                } else { // ---------> If buy not allowed i.e. we need to sell

                    int sellKaro = prices[index] + dp[index + 1][1];
                    int skipKaro =  dp[index + 1][0];

                    profit = Math.max (sellKaro , skipKaro);
                }

                dp[index][buy] = profit;
            }
        }
        /*

           Tabulation main hame woh hee return karna hotaa hain jo hamne Recursion yaa fir Memoization ke parameters main bheja hain.

         */

        return dp[0][1];

    }


    // ################### Space Optimization ####################

    /*

             dp[index][buy] depends on profit and profit depends on sellKaro/buyKaro , skipKaro
             and sellKaro/buyKaro , skipKaro  depends on dp[index+1][1]  and dp[index+1][0]



             dp[index][buy] -------------------> dp[index+1][1]
                           \
                             \
                               \
                                 \
                                  dp[index+1][0]


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

        // Both the rows contains nly two blocks i.e. [2]
        int []currRow = new int[2];
        int []nextRow = new int[2];


        for (int index = n-1 ; index >= 0 ; index--) {

            for (int buy = 0 ; buy <= 1 ; buy++) {

                int profit = 0;
                if (buy == 1) {

                    int buyKaro = -prices[index] + nextRow[0];
                    int skipKaro =  nextRow[1];

                    profit = Math.max (buyKaro , skipKaro);

                } else { // ---------> If buy not allowed i.e. we need to sell

                    int sellKaro = prices[index] + nextRow[1];
                    int skipKaro =  nextRow[0];

                    profit = Math.max (sellKaro , skipKaro);
                }

                currRow[buy] = profit;
            }

            nextRow = currRow;
        }

        return nextRow[1];

    }


}
