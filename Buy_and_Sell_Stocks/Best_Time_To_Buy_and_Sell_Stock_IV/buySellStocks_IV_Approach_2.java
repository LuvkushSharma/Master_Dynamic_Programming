package Buy_and_Sell_Stocks.Best_Time_To_Buy_and_Sell_Stock_IV;

/*

Ex : {3  ,   2   ,  6  ,   5  ,  0  , 3}  where k = 2
             B      S            B    S


             total_profit = (6 - 2) + (3 - 0) ===> 7

             So, we will consider B and S as an operation.

             i.e.

             {3  ,   2   ,  6  ,   5  ,  0  , 3}  where k = 2
                     B      S            B    S
                   Optn-0  Optn-1     Optn-2  Optn-3


             So, maximum operation allowed are : (2 * k)

 ----------->  B     S       B      S
              Optn-0 Optn-1 Optn-2 Optn-3

              So, if we look carefully then we can see that Buy (B) operation starts with an even number whereas S (Sell) starts with
              an odd number.


 */

// $$$$$$$$$$$$$$$$$$$$$$$$$$$$ Approach-2 $$$$$$$$$$$$$$$$$$$$$$$$$$$$

// --------> Using Operation Number

import java.util.Arrays;

public class buySellStocks_IV_Approach_2 {

    public static void main(String[] args) {

        int k = 2;
        int []prices = {3,2,6,5,0,3};

        // --------- Recursion --------
        int ans1 = solve_recur (prices , 0 , 0 , k);
        System.out.println(ans1);


        // ---------- Memoization -------
        int n = prices.length;

        // Step-1 :

        // Max value of index can be 'n'
        // Max number of operations can be 2*k
        int [][]dp = new int[n][2*k];
        for (int []arr : dp)
            Arrays.fill (arr , -1);

        int ans2 = solve_memo (prices , 0 , 0 , k , dp);
        System.out.println(ans2);

        // ---------- Tabulation --------
        int ans3 = solve_tabu (prices , k);
        System.out.println(ans3);

        // ---------- Space Optimisation --------
        int ans4 = solve_so (prices , k);
        System.out.println(ans4);


    }

    // ###################### Recursion ######################

    public static int solve_recur (int []prices , int index , int operationNo , int k) {

        // Base Case
        if (index == prices.length)
            return 0;

        // Abb rukna hain.
        if (operationNo == 2*k)
            return 0;

        int profit = 0;

        // As operationNo is even i.e. buy karna allowed hain.
        if (operationNo % 2 == 0) {

            int buyKaro = -prices[index] + solve_recur (prices , index + 1 , operationNo + 1 , k);
            int skipKaro =  solve_recur (prices , index + 1 , operationNo , k);

            profit = Math.max (buyKaro , skipKaro);

        } else {

            int sellKaro = prices[index] + solve_recur (prices , index + 1 , operationNo + 1 , k);
            int skipKaro =  solve_recur (prices , index + 1 , operationNo , k);

            profit = Math.max (sellKaro , skipKaro);
        }

        return profit;

    }

    // ###################### Memoization ######################
    /*

       Out of 5 parameters in the recursive function : Only two are changing i.e. index and operationNo i.e. 2D Dp

     */

    public static int solve_memo (int []prices , int index , int operationNo , int k , int [][]dp) {

        // Base Case
        if (index == prices.length)
            return 0;

        // Abb rukna hain.
        if (operationNo == 2*k)
            return 0;


        // Step-3
        if (dp[index][operationNo] != -1) {

            return dp[index][operationNo];
        }

        int profit = 0;

        // As operationNo is even i.e. buy karna allowed hain.
        if (operationNo % 2 == 0) {

            int buyKaro = -prices[index] + solve_memo (prices , index + 1 , operationNo + 1 , k , dp);
            int skipKaro =  solve_memo (prices , index + 1 , operationNo , k , dp);

            profit = Math.max (buyKaro , skipKaro);

        } else {

            int sellKaro = prices[index] + solve_memo (prices , index + 1 , operationNo + 1 , k , dp);
            int skipKaro =  solve_memo (prices , index + 1 , operationNo , k , dp);

            profit = Math.max (sellKaro , skipKaro);
        }

        // Step-2 :
        return dp[index][operationNo] = profit;

    }


    // #########################  Tabulation ###################

    /*

    In memoization : index goes from 0 to n-1
                     operationNo goes from 0 to 2*k

    In Tabulation : index goes from n-1 to 0


     */

    public static int solve_tabu (int []prices , int k) {

        int n = prices.length;

        // Step-1 :
        int [][]dp = new int [n+1][2*k + 1];

        // Step-2 : Analyze the base cases : Already done

        for (int index = n-1; index >= 0 ; index--) {

            // for '2*k' we had already done the filling
            for (int operationNo = 0 ; operationNo < 2*k ; operationNo++) {

                int profit = 0;

                // As operationNo is even i.e. buy karna allowed hain.
                if (operationNo % 2 == 0) {

                    int buyKaro = -prices[index] + dp[index + 1][operationNo + 1];
                    int skipKaro =  dp[index + 1][operationNo];

                    profit = Math.max (buyKaro , skipKaro);

                } else {

                    int sellKaro = prices[index] + dp[index + 1][operationNo + 1];
                    int skipKaro =  dp[index + 1][operationNo];

                    profit = Math.max (sellKaro , skipKaro);
                }

                dp[index][operationNo] = profit;
            }
        }

        return dp[0][0];
    }

    // #################### Space Optimisation #################

    public static int solve_so (int []prices , int k) {

        int n = prices.length;

        int []nextRow = new int[2*k + 1];
        int []currRow = new int[2*k + 1];

        // Step-2 : Analyze the base cases : Already done

        for (int index = n-1; index >= 0 ; index--) {

            // for '2*k' we had already done the filling
            for (int operationNo = 0 ; operationNo < 2*k ; operationNo++) {

                int profit = 0;

                // As operationNo is even i.e. buy karna allowed hain.
                if (operationNo % 2 == 0) {

                    int buyKaro = -prices[index] + nextRow[operationNo + 1];
                    int skipKaro =  nextRow[operationNo];

                    profit = Math.max (buyKaro , skipKaro);

                } else {

                    int sellKaro = prices[index] + nextRow[operationNo + 1];
                    int skipKaro =  nextRow[operationNo];

                    profit = Math.max (sellKaro , skipKaro);
                }

                currRow[operationNo] = profit;
            }

            nextRow = currRow;
        }

        return nextRow[0];
    }


}
