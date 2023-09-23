package DP_1D.Lec_9;

/*

Given a number N. Find the minimum number of squares of any number that sums to N. For Example: If N = 100 ,
N can be expressed as (10*10) and also as (5*5 + 5*5 + 5*5 + 5*5) but the output will be 1 as minimum number of square is 1 , i.e (10*10).


Example 1:

Input: N = 100
Output: 1
Explanation: 10 * 10 = 100
Example 2:

Input: N = 6
Output: 3
Explanation = 1 * 1 + 1 * 1 + 2 * 2 = 6


Expcted Time Complexity: O(N * sqrt(N) )
Expected Space Complexity: O(N)


Constraints:
1 <= N <= 10000


 */


/*

    Let's say mujhe 5 banaana hain so, we need to find kii main 5 kis kis ke perfect squares se bana sakta huun.

    Kya : 1^2 se 5 ban sakta hain : Yes !
    Kya : 2^2 se 5 ban sakta hain : Yes !
    Kya : 3^2 se 5 ban sakta hain : No !

    iska matlab maine :
    for (int i = 1 , i*i <= n yaa fir i <= sqrt(n) ; i++) {...}  kiya hoga.

    This question approach is similar to the Combination Sum IV



                                              5
                                   1^2    /       \    2^2
                                        /           \
                                      4              1 --------> Overlapping sub-problems
                              1^2   /                            As in the left call we already visited n = 1
                                  /
                                3
                       1^2    /
 neeche se returned         /
   ----> (1+1=2)          2
                  1^2   /  \ 2^2 ---> No
                      /
   ----> (0+1)       1  ----------> iss 1 kaa matlab hain : min number of ways to create 1
             1^2  /   \ 2^2
                /       \
              0        Kya main 4 se 1 bana sakta huun. So, ham call hee nhi lagaayengye aaise case main.
              |
              |
              v
       JAB BHI MERA N == 0 hoga then woh base case hoga.
       Because : Constraint is N >= 1
       So, ham N = 0 ko kisi se nhi bana sakte.

       return 0


       So, when n = 1 hoga then neeche se 0 ayega so, we need to add + 1 in it

 */

import java.util.Arrays;

public class Perfect_Squares {

    public static void main(String[] args) {

        int n = 100;

        // --------------- Recursion -------------
//        int ans = solve_recur (n);
//        System.out.println(ans);


        // --------------- Memoization -------------
        // Step-1 :
        int []dp = new int[n + 1];

        Arrays.fill (dp , -1);

        int ans1 = solve_memo (n , dp);
        System.out.println(ans1);

        // -------------- Tabulation --------------
        int ans2 = solve_tabu (n);
        System.out.println(ans2);

    }

    // ###################### Recursion ###################
    public static int solve_recur (int n) {

         // Base Case
         if (n == 0) {

             return 0;
         }

         int ans = n;
         for (int i = 1 ; i*i <= n ; i++) {

             ans = Math.min (ans , 1 + solve_recur(n - (i * i)));
         }

         return ans;
    }

    // ###################### Memoization ###################
    /*
         Recursive call dekho usme sirf ek hee parameter change ho raha hain i.e. n
         i.e. we had applied 1D DP

     */
    public static int solve_memo (int n , int []dp) {

        // Base Case
        if (n == 0) {

            return 0;
        }

        // Step-3:
        if (dp[n] != -1) {

            return dp[n];
        }

        int ans = n;
        for (int i = 1 ; i*i <= n ; i++) {

            int ps = i*i;
            ans = Math.min (ans , 1 + solve_memo(n - ps , dp));
        }

        dp[n] = ans;

        // Step-2 :
        return dp[n];
    }


    // ###################### Tabulation ###################
    public static int solve_tabu (int n) {

        // Step-1 :
        int []dp = new int[n + 1];

        Arrays.fill (dp , Integer.MAX_VALUE);

        // Step-2 : Analyze the base case
        dp[0] = 0;

        // Step-3 :
        // 1 to n

        for (int i = 1 ; i <= n ; i++) {

            // Har ek n ke liye ham ye loop chala rahe the
            for (int j = 1 ; j*j <= n ; j++) {

                int ps = j*j;

                // Checking Valid Index
                if ((i - ps) >= 0) {

                    dp[i] = Math.min (dp[i] , 1 + dp[i - ps]);
                }
            }
        }

        return dp[n];
    }

    // ####################### Space Optimization ###############
    /*
          dp[i] kis par depend karta hain : (1 + dp[i - j*j]) par depend karta hain.

          Let's say dp[5]   ===> dp[5 - 1^2] => dp[4]
                            ===> dp[5 - 2^2] => dp[1]

          Let's say dp[50]   ===> dp[50 - 1^2] => dp[49]
                            ===> dp[50 - 2^2] => dp[46]
                            ===> dp[50 - 3^2] => dp[41]
                            ===> dp[50 - 4^2] => dp[34]
                            ===> dp[50 - 5^2] => dp[25]
                            ===> dp[50 - 6^2] => dp[14]
                            ===> dp[50 - 7^2] => dp[1]

                            No , exact pattern is there so, that we can optimise the code.

     */

}
