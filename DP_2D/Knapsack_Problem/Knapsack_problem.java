package DP_2D.Knapsack_Problem;

/*

A thief is robbing a store and can carry a maximal  weight of W into his knapsack. There are N items and the ith item weighs wi and is of value vi.
Considering the constraints of the maximum weight that a knapsack can carry, you have to find and return the maximum value that a thief can generate by stealing items.

Sample Input:

1
4
1 2 4 5
5 4 8 6
5

Sample Output:
13


 */

/*

Knapsack -----> W = 5

  w      -->  1       2        4        5
(weight)

 v       -->  5       4        8        6
 (value)

 Note : Bag can store only 5kg of items.

 Let's say i choose last item having weight = 5 ----> Profit/value => 6

 i.e. {5} ---> value = 6

 {1,4} ---> 8 + 5 so, value = 13

 {1,2} ---> 5 + 4 so, value = 9

 Hence , we select {1,4} so, that we can get the maximum profit.


 --------------> Brute Force Approach --------------

 Let's say i have only 3 items : A , B and C

 1st Combination : {A}
 2nd Combination : {B}
 3rd Combination : {C}
 4th Combination : {A,B}
 5th Combination : {A,C}
 6th Combination : {B,C}
 7th Combination : {A,B,C}
 8th Combination : {}

 So, we have 8 combinations and we will find the cummulative values of all the combinations and the combination having higher
 values will be choosen.

 So, same will be applied to the Knapsack example having W = 5. i.e. we will find all the combinations of weights
 which cummulatively lesser or equal to W. And then combination having higher weight will win. e.g. {1,4} was the combination
 that has higher value i.e. 13 i.e. it wons.

 So, we will use INCLUSION AND EXCLUSION Method to solve this problem.

 ---------------------------------------------------------------------------------

 Now , let's say we have 3 items I , II and III

                                      i
                                    { I  , II  , III}
                                     / \
                             INC   /     \ EXC
                                 /         \
                                 i
              ans = {I}    {I , II , III}
                               /  \
                        INC  /      \  EXC
                           /          \
                             i
   ans = {I,II}   {I , II , III}
                            /   \
                     INC   /      \
                          /         \
                                   i   {I , II , III}  ans => {I , II} -----> Base case
   ans = {I,II,III} {I , II , III}
                               ------> now i crosses i.e. base condition so, maxVal = max (maxVal , total_val)
 */

import java.util.Arrays;

public class Knapsack_problem {

    public static void main(String[] args) {

        int W = 5;
        int []weight = {1 , 2 , 4 , 5};
        int []value = {5 , 4 , 8 , 6};

        int n = weight.length;

        // ------------------   Recursion -------------------

        int ans = solve (weight , value , n - 1 , W);
        System.out.println(ans);

        // ----------------- Memoization -----------------

        int [][]dp = new int[n][W + 1];

        for (int arr[] : dp) {

            Arrays.fill (arr , -1);
        }

        int ans1 = solve_memo (weight , value , n - 1 , W , dp);
        System.out.println(ans1);

        // ----------------- Tabulation (Bottom Up) -------------------
        int ans2 = solve_Tabu (weight , value , W);
        System.out.println(ans2);

        // ----------------- Space Optimization ---------------------
        int ans3 = solve_SO (weight , value , W);
        System.out.println(ans3);

        // ----------------- Space Optimization (Level-2) -------------
        int ans4 = solve_SO_2 (weight , value , W);
        System.out.println(ans4);

    }


    // ######################## Recursion ######################

    public static int solve (int weight[] , int value[] , int index , int capacity) {

        // Base Case
        // Let's say i had started from the last index so, index == 0 will be the base case.
        // If only 1 item to steal, then just compare its weight with the knapsack capacity.
        if (index == 0) {

            // 0th element ke pass agar capacity hain toh usse include karlo.
            if (weight[0] <= capacity) {

                return value[0];

            } else {

                return 0;
            }
        }

        int include = 0;
        if (weight[index] <= capacity) {

            // weight[index] included hua hain isliye Profit uska add ho raha hain include main.
            include = value[index] + solve (weight, value, index-1, capacity - weight[index]);
        }

        int exclude = 0 + solve (weight, value, index-1, capacity);

        int ans = Math.max (include , exclude);

        return ans;
    }



    // ######################## Memoization ######################


    //  Ye kaise patah chala kii 2D DP lagega.
    // Dhyaan se dekho kis kis variable kii state change ho rahi hain.
    // 1) index
    // 2) capacity
    // weight and values remains same and only index and capacity are changing i.e. 2D DP.
    public static int solve_memo (int weight[] , int value[] , int index , int capacity , int[][]dp) {

        // Base Case
        if (index == 0) {

            // 0th element ke pass agar capacity hain toh usse include karlo.
            if (weight[0] <= capacity) {

                return value[0];

            } else {

                return 0;
            }
        }

        if (dp[index][capacity] != -1) {

            return dp[index][capacity];
        }

        int include = 0;
        if (weight[index] <= capacity) {

            include = value[index] + solve_memo  (weight, value, index-1, capacity - weight[index] , dp);
        }

        int exclude = solve_memo  (weight, value, index-1, capacity , dp);

        dp[index][capacity] = Math.max (include , exclude);

        return dp[index][capacity];
    }

    // ######################## Tabulation ######################

    public static int solve_Tabu (int weight[] , int value[] , int capacity) {

        int n = weight.length;

        // Step-1 :
        int [][]dp = new int[n][capacity + 1];

        // Step-2 : Analyze the Base case : Since for index == 0 i.e. for row = 0 we have to use the logic in the base case.
        for (int w = weight[0] ; w <= capacity ; w++) {

            if (weight[0] <= capacity) {

                dp[0][w] = value[0];

            } else {

                dp[0][w] = 0;

            }
        }


        // Since , row i.e. weight[] goes from 0 to n-1 , but we already handled the 0 waala case
        // i.e. row : 1 to n-1
        // and col goes from 0 to capacity

        // Step-3 : Take care of remaining recursive calls
        for (int index = 1 ; index < n ; index++) {

            for (int w = 0 ; w <= capacity ; w++) {

                int include = 0;
                if (weight[index] <= w) {

                    include = value[index] + dp[index-1][w - weight[index]];
                }

                int exclude = dp[index-1][w];

                dp[index][w] = Math.max (include , exclude);

            }
        }
        return dp[n-1][capacity];
    }

      // ######################## Space Optimization ######################

    /*
           For , finding space optimization soln first we need to find the dependency of dp.

           dp is dependent on include and exclude.
           include dependent on dp[index-1][w - weight[index]]
           exclude depends on dp[index-1][w]

                                          Depends on
           Hence : dp[n-1][capacity] -------------------------> dp[index-1][w - weight[index]]
                                     \
                                       \
                                         \
                                           \
                                             \
                                               \
                                               dp[index-1][w]

           Jis bhi row kaa ham answer nikaal rahe hain then woh upar waali row par depend kar raha hain.
           And dono ke case main i.e. include and exclude ke case main ham (index-1) par hee depend hain.

           So, puuri dp kii jagye ham sirf 2 rows hee banaayengye.
           Ek : curr_row and ek prev_row


           Row-1   --------->  prev (so, base case analyzation main pervRow hee fill hogyi

           Row-2   --------->  curr

           Row-3   --------->

           Row-4   --------->

           .
           .
           .

           Iske baad ham dekh rahe hain kii index neeche aa raha hain i.e. 1 to n-1
           i.e. abb prevRow neeche aayega currRow kii jagye lene
     */

    public static int solve_SO (int weight[] , int value[] , int capacity) {

        int n = weight.length;

        // Step-1 :
        int []prevRow = new int[capacity + 1];
        int []currRow = new int[capacity + 1];

        // Step-2 : Analyze the Base case : Since for index == 0 i.e. for row = 0 we have to use the logic in the base case.
        for (int w = weight[0] ; w <= capacity ; w++) {

            if (weight[0] <= capacity) {

                prevRow[w] = value[0];

            } else {

                prevRow[w] = 0;

            }
        }


        // Step-3 : Take care of remaining recursive calls
        for (int index = 1 ; index < n ; index++) {

            for (int w = 0 ; w <= capacity ; w++) {

                int include = 0;
                if (weight[index] <= w) {

                    include = value[index] + prevRow[w - weight[index]];
                }

                int exclude = prevRow[w];

                currRow[w] = Math.max (include , exclude);

            }

            // Moving prevRow to the nextRow
            prevRow = currRow;
        }
        return prevRow[capacity];
    }

    // ######################## Level-2 Space Optimization ######################

    /*

            We , can further optimise this code i.e. instead of taking two arrays i.e. prevRow and CurrRow
            we will try to take only one.

            We can see that : currRow[w] is depending on prev[w] and prev[w - weight[index]]

            i.e. instead of taking prevRow we'll try to cope up with the single array.

            prevRow :

            ----------------------------------------------
            |      |       |      |   X    |       |     |
            ----------------------------------------------
                                      ^
                                      |
                                     prevRow[w]



            currRow :

            ----------------------------------------------
            |      |       |      |   X    |       |     |
            ----------------------------------------------
                                     ^
                                     |
                                   currRow[w]

            so, currRow[w] is dependent on prevRow[w] and elements from 0 to w in the prevRow[]

            i.e.

            prevRow :

            ----------------------------------------------
            |      |       |      |   X    |       |     |
            ----------------------------------------------
             |                             |
             -------------------------------
                   This is the range that i
                   am talking about so, we need
                   prevRow[w] and it's previous element
                   and even we donot elements
                   beyond prevRow[w]

             Hence , we will take a currRow and will be dependent on currRow[w] and currRow[w - weight[index]]


           ⚠️💀💀💀💀💀💀💀💀  ⚠️

           But , this won't work

           currRow :

            ----------------------------------------------
            |      |       |      |   X    |       |     |
            ----------------------------------------------
                                     ^
                                     |
                                   currRow[w]
            |                     |
            -----------------------
              This range includes
              currRow[w - weight[index]]

              Let's say X = 3

              so, currRow[w] yaa fir toh iss 3 se niklega yaa fir peeche waali range se
              Let's say ham calculate karne gaye and answer aa gaya 7
              so, currRow[w] = 7 now, i.e. X is updated to 7 from 3.


            -----------------------------------------------
            |      |       |      |   7    |       |  X   |
            -----------------------------------------------
                                                     ^
                                                     |
                                                 currRow[w]

             Let's say X = 5 on that index i.e. index on which currRow[w] currently pointing.
             Abb maan lo main iss X par aa gaya nad currRow[w] ko calculate karne laga

             Abb isme calculate karte hue ye waala case aata hain i.e. currRow[w - weight[index]]
             Jo kii peeche waali range main kisi par depend karega.
             Maan lo woh pichle X i.e. 7 par hee depend kar gaya toh answer galat ayega.
             Becuase pehle waha 3 thaa Originally not 7.

             Current X is expecting kii currRow[w - weight[index]] = 3 hoga but yaha par 7 hain.

             That is why main Left to Right agar iss Logic ko lagaane kii kosis karunga toh ye kaam nhi karega.

             😀
             But Right to Left this works.

            -----------------------------------------------
            |      |       |      |        |       |  X   |
            -----------------------------------------------
                                                     ^
                                                     |
                                                 currRow[w]

            |                                      |
            ----------------------------------------
                       Range that is intact of overwritten


            Now , right to left main agar mujhe X nikaalna hain toh X : currRow[w] par depend kar raha hain
            and peeche waali range par jo kii abhi tak change hee nhi hui.

     */

    public static int solve_SO_2 (int weight[] , int value[] , int capacity) {

        int n = weight.length;

        // Step-1 :
        int []currRow = new int[capacity + 1];

        // Step-2 : Analyze the Base case : Since for index == 0 i.e. for row = 0 we have to use the logic in the base case.
        for (int w = weight[0] ; w <= capacity ; w++) {

            if (weight[0] <= capacity) {

                currRow[w] = value[0];

            } else {

                currRow[w] = 0;

            }
        }


        // Step-3 : Take care of remaining recursive calls
        for (int index = 1 ; index < n ; index++) {

            for (int w = capacity ; w >= 0 ; w--) {

                int include = 0;
                if (weight[index] <= w) {

                    include = value[index] + currRow[w - weight[index]];
                }

                int exclude = currRow[w];

                currRow[w] = Math.max (include , exclude);

            }

        }
        return currRow[capacity];
    }

}

/*

------------------------- 😜👍👍👍👍👍👍👍👍👍👍👍👍😜 ---------------------

Space Complexity of Tabulation soln is : O(n X W)

Space Complexity of Space Optimization soln is : O(2 X W)

Space Complexity of Space Optimization Level-2 soln is : O(W)


 */