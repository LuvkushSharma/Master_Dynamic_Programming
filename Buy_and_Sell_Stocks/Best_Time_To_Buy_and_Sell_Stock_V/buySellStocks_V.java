package Buy_and_Sell_Stocks.Best_Time_To_Buy_and_Sell_Stock_V;

/*

You are given an array prices where prices[i] is the price of a given stock on the ith day, and an integer fee representing a transaction fee.

Find the maximum profit you can achieve. You may complete as many transactions as you like, but you need to pay the transaction fee for each transaction.

Note:

You may not engage in multiple transactions simultaneously (i.e., you must sell the stock before you buy again).
The transaction fee is only charged once for each stock purchase and sale.


Example 1:

Input: prices = [1,3,2,8,4,9], fee = 2
Output: 8
Explanation: The maximum profit can be achieved by:
- Buying at prices[0] = 1
- Selling at prices[3] = 8
- Buying at prices[4] = 4
- Selling at prices[5] = 9
The total profit is ((8 - 1) - 2) + ((9 - 4) - 2) = 8.

Example 2:

Input: prices = [1,3,7,5,10,3], fee = 3
Output: 6


Constraints:

1 <= prices.length <= 5 * 104
1 <= prices[i] < 5 * 104
0 <= fee < 5 * 104


 */

/*

Transaction is the combination of buy and sell. So, whenever we completes a transaction then we need to pay the transaction fee.

So, multiple transactions are allowed but , we need to pay the fee after each transaction.


------> This question is similar to the Part-2 of the best time and sell stocks.


 */

public class buySellStocks_V {

    public static void main(String[] args) {

        int []prices = {1,3,2,8,4,9};
        int fee = 2;

        int ans1 = solve_so (prices , fee);
        System.out.println(ans1);
    }

    public static int solve_so (int []prices , int fee) {

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

                    int sellKaro = prices[index] + nextRow[1] - fee;  // paying the fee after the transaction.
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
