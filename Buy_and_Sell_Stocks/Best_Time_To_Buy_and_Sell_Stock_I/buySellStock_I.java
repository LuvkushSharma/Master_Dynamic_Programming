package Buy_and_Sell_Stocks.Best_Time_To_Buy_and_Sell_Stock_I;

/*

You are given an array prices where prices[i] is the price of a given stock on the ith day.

You want to maximize your profit by choosing a single day to buy one stock and choosing a different day in the future to sell that stock.

Return the maximum profit you can achieve from this transaction. If you cannot achieve any profit, return 0.



Example 1:

Input: prices = [7,1,5,3,6,4]
Output: 5
Explanation: Buy on day 2 (price = 1) and sell on day 5 (price = 6), profit = 6-1 = 5.
Note that buying on day 2 and selling on day 1 is not allowed because you must buy before you sell.

Example 2:

Input: prices = [7,6,4,3,1]
Output: 0
Explanation: In this case, no transactions are done and the max profit = 0.


Constraints:

1 <= prices.length <= 105
0 <= prices[i] <= 104


 */

/*

          prices :   { 7  ,  1   ,  5  ,   3   ,  6  ,   4}
                       |     |      |      |      |      |
                       V     V      V      V      V      v
                    Day_1  Day_2   Day_3   Day_4  Day_5  Day_6



                    Let's say maine Day_2 ko buy kar liaa thaa and Day_5 ko sell kar diaa thaa
                    i.e. profit = 6 - 1 === 5

                    We can buy any day and sell on any day but we have to first buy and then sell.


          ------------------------------------ Intuition ---------------------------------------------------


          We need maximum possible difference between sell and buy so, that we get maximum profit.

              0       1       2       3      4       5
          -------------------------------------------------
          |   7   |   1   |   5   |   3   |   6   |   4   |
          -------------------------------------------------

          Maine kaha main sell karne kaa day select kar leta huun.

          Let's say main '6' par sell kar raha huun toh iska matlab : maine 0 to 3 index main kisi day par buy kiya hoga.
          Itni range main ussi din buy kiya hoga jo bhi minimum hoga.

          Similarly , agar main Day_3 main sell kar raha huun then maine Day_1 yaa fir Day_2 main se kisi day main buy kiya hoga.

          And , if i sell on Day_4 then common sense kii baat hain kii main Day_1 , Day_2 and Day_3 main se kisi day par buy kiya hoga.
          And we'll buy using min number.


          Note : We cannot sell on the same day i.e. if we buy on Day_1 i.e. index = 0 then we cannot sell on Day_1 i.e. index = 0.
                 We can only sell any day i.e. different day but after buying stock.


          --------> mini = arr[0]
          --------> profit = 0

          Abb main idx = 1 to idx = price.length-1 tak sell kar sakta huun.

          for (int i = 1 ; i < n ; i++) {

               // diff = (jiss din sell kiya hain) - (minimum value)
               // i.e. agar main aaj ke din i.e. ith day sell karta toh mujhe ye profit milta.
               int diff = arr[i] - mini;

               profit = max (profit , diff)

               mini = min (mini , arr[i])
          }


 */

public class buySellStock_I {

    public static void main(String[] args) {

        int []price = {7,1,5,3,6,4};

        int mini = price[0];
        int profit = 0;


        // 'i' is the day on which we are selling the stock.
        /*
            i.e.

            if i am selling on 'ith' day then
            buying on : 1 to (i - 1)th day

         */
        for (int i = 1 ; i < price.length ; i++) {

            int diff = (price[i] - mini);

            profit = Math.max (profit , diff);

            mini = Math.min (mini , price[i]);
        }

        System.out.println(profit);
    }
}
