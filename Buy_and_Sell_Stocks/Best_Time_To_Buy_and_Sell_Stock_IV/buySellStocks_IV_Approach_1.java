package Buy_and_Sell_Stocks.Best_Time_To_Buy_and_Sell_Stock_IV;

/*

You are given an integer array prices where prices[i] is the price of a given stock on the ith day, and an integer k.

Find the maximum profit you can achieve. You may complete at most k transactions: i.e. you may buy at most k times and sell at most k times.

Note: You may not engage in multiple transactions simultaneously (i.e., you must sell the stock before you buy again).



Example 1:

Input: k = 2, prices = [2,4,1]
Output: 2
Explanation: Buy on day 1 (price = 2) and sell on day 2 (price = 4), profit = 4-2 = 2.

Example 2:

Input: k = 2, prices = [3,2,6,5,0,3]
Output: 7
Explanation: Buy on day 2 (price = 2) and sell on day 3 (price = 6), profit = 6-2 = 4. Then buy on day 5 (price = 0) and sell on day 6 (price = 3), profit = 3-0 = 3.


Constraints:

1 <= k <= 100
1 <= prices.length <= 1000
0 <= prices[i] <= 1000


 */

/*

Part-1 : In this part we are allowed to Buy one stock and sell that stock. Note : First we need to buy the stock then only we can sell that.

Part-2 : We can do infinite transactions but , we need to buy the stock before selling.

Part-3 : In this part , we are allowed two transactions at a time i.e. Buy sell and then Buy sell


Part-4 : Now , in this part , we are allowed to do at most k transactions.


Ex : {3  ,   2   ,  6  ,   5  ,  0  , 3}  where k = 2
             B      S            B    S

             total_profit = (6 - 2) + (3 - 0) ===> 7


 */

// $$$$$$$$$$$$$$$$$$$$$$$$$$$$   Approach-1    $$$$$$$$$$$$$$$$$$$$$$$$$$$$

public class buySellStocks_IV_Approach_1 {

    public static void main(String[] args) {

           int k = 2;
           int []prices = {3,2,6,5,0,3};

            // ----------- Space Optimisation --------
            int ans4 = solve_so (prices , k);
            System.out.println(ans4);
    }



    // ######################## Space Optimisation #######################
    // TC : O(n * 2 * k)
    // SC : O(k)
    public static int solve_so (int []prices , int k) {

        int n = prices.length;

        // Step-1 :

        // Both the rows contains only two blocks i.e. [2] for buy and 'k' blocks transactions which is are limit.
        int [][]currRow = new int[2][k + 1];
        int [][]nextRow = new int[2][k + 1];


        for (int index = n-1 ; index >= 0 ; index--) {

            for (int buy = 0 ; buy <= 1 ; buy++) {

                for (int limit = 1 ; limit <= k ; limit++) {

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

        return nextRow[1][k];

    }


}
