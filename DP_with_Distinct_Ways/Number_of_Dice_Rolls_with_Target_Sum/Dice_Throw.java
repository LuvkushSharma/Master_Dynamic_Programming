package DP_with_Distinct_Ways.Number_of_Dice_Rolls_with_Target_Sum;

/*

Given N dice each with M faces, numbered from 1 to M, find the number of ways to get sum X.
X is the summation of values on each face when all the dice are thrown.


Example 1:

Input:
M = 6, N = 3, X = 12
Output:
25

Explanation:
There are 25 total ways to get
the Sum 12 using 3 dices with
faces from 1 to 6.

Example 2:

Input:
M = 2, N = 3, X = 6
Output:
1

Explanation:
There is only 1 way to get
the Sum 6 using 3 dices with
faces from 1 to 2. All the
dices will have to land on 2.


Your Task:
You don't need to read input or print anything. Your task is to complete the function noOfWays() which takes 3 Integers M,N and X
as input and returns the answer.


Expected Time Complexity: O(M*N*X)
Expected Auxiliary Space: O(N*X)



Constraints:
1 <= M,N,X <= 50


 */


/*

          Input-1 : N = 3 , M = 6 and X = 12

          So, we have 3 dices having faces from 1 to 6

          D1              D2           D3
       /     \         /     \      /      \
      1 ..... 6       1 ..... 6    1 .....  6


      Any number from 1 to 6 can occur through dice-1 when we roll it.
      Similarly , with dice 2 and dice 3.

      ---------------------------------
      |          |          |         |
      |   6      |    6     |    6    |
      ---------------------------------

      6 * 6 * 6 ===> 216 Combinations

      And out of 216 , we have 25 combinations such that when we add numbers on each dice then we get X.

      Ex : D1 = 1  , D2 = 5 and D3 = 6
          So, in this combination of faces we are getting sum as 12

      Ex : D1 = 1 , D2 = 6 and D3 = 5 ,.....



      Input-2 : M = 2 , N = 3 and X = 6

          D1              D2           D3
       /     \         /     \      /      \
      1       2       1       2    1        2

         When we throw each dice then we can get 1 or 2 i.e. 2 possibilities from a single dice.

         2 * 2 * 2 ===> 8

         i.e. 1 , 1 , 1
              1 , 1 , 2
              1 , 2 , 1
              1 , 2 , 2
              .
              .
              .
              2 , 2 , 2 --------> 1 possibility when we get a sum of 6

              i.e. ans = 1


 */

/*

------------------------ Approach ------------------------


                                 // Where 'i' represents face number.
                                 for (int i = 1 ; i <= faces ; i++) {

                                    // Maine nth dice use kar lia i.e. numOfDices-1
                                    ans = ans + solve_recur (numOfDices - 1 , faces , target - i);
                                }





                                       i
                                      D1              D2           D3
                                   /     \         /     \      /      \
                                  1 ..... 6       1 ..... 6    1 .....  6
                                      /
                                     /
                                    /
                                i  /
                         {D1 , D2 , D3 , 1 , 6}
                              /  \
                             1    2
                             /
                            /
                           /    i
                     {D1 , D2 , D3 , 2 , 6}
                               /  \
                              1    2
                           /
                          /
                         /      i
                     {D1 , D2 , D3 , 3 , 6}
                               /  \
                              1    2
                            /
                           /
                          /          i
                     {D1 , D2 , D3 }
                                     |
                                     |
                                     v
                             Base Case Occurs
                             return and call for 2 , Since for sum != 6




 */

import java.util.Arrays;

public class Dice_Throw {

    public static void main(String[] args) {

        int M = 6;
        int N = 3;
        int X = 12;

        // -------------------- Recursion ------------------
        int ans1 = solve_recur (N , M , X);
        System.out.println(ans1);

        // -------------------- Memoization ------------------
        int [][]dp = new int[N+1][X+1];

        for (int arr[] : dp) {
            Arrays.fill (arr , -1);
        }
        int ans2 = solve_memo (N , M , X , dp);
        System.out.println(ans2);

        // -------------------- Tabulation ------------------
        int ans3 = solve_tabu (N , M , X);
        System.out.println(ans3);

        // -------------------- Space Optimization  ------------------
        int ans4 = solve_so (N , M , X);
        System.out.println(ans4);
    }

    // ##################### Recursion ###################
    public static int solve_recur (int numOfDices , int faces , int target) {

        // Base Case
        if (target < 0) {
            return 0;
        }

        // i.e. Fekne ke liye dice nhi hain but we have to make a target.
        if (numOfDices == 0 && target != 0) {
            return 0;
        }

        // Target '0' hain but numOfDices khatam nhi hue
        // i.e. kya ham bache hue dice se target = 0 bana sakte hain ?
        // Nhi , because '0' type kaa face hee nhi hota.
        if (target == 0 && numOfDices != 0) {
            return 0;
        }

        // Mere pass dice bhi nhi hain and target bhi 0 hain
        if (numOfDices == 0 && target == 0) {
            return 1;
        }

        int ans = 0;
        for (int i = 1 ; i <= faces ; i++) {

            // Maine nth dice use kar lia i.e. numOfDices-1
            ans = ans + solve_recur (numOfDices - 1 , faces , target - i);
        }

        return ans;
    }

    // ##################### Memoization ###################
    /*
        When we look at recursive call then we can see that two we have total : 3 variables
        and out of 3 variables : 2 are varying.

        i.e. 2D Dp

     */
    public static int solve_memo (int numOfDices , int faces , int target , int [][]dp) {

        // Base Case
        if (target < 0) {
            return 0;
        }

        if (numOfDices == 0 && target != 0) {
            return 0;
        }

        if (target == 0 && numOfDices != 0) {
            return 0;
        }

        if (numOfDices == 0 && target == 0) {
            return 1;
        }

        // Step-3
        if (dp[numOfDices][target] != -1) {
            return dp[numOfDices][target];
        }

        int ans = 0;
        for (int i = 1 ; i <= faces ; i++) {

            // Maine nth dice use kar lia i.e. numOfDices-1
            ans = ans + solve_memo (numOfDices - 1 , faces , target - i , dp);
        }

        // Step-2
        dp[numOfDices][target] = ans;

        return ans;
    }


    // ##################### Tabulation ###################

    /*

        Top Down : 👍
        numOfDices ----> N to 0
        target -----> X to 0

        Bottom Up : ✅ 🆗
        numOfDices ----> 0 to N
        target -----> 0 to X

     */

    public static int solve_tabu (int d , int faces , int t) {

        // Step-1 :
        int [][]dp = new int[d + 1][t + 1];

        // Step-2 :
        dp[0][0] = 1;

        // dice = 0 and target = 0 ke liye hamne already fill kar dia hain upar i.e. dice : 1 to N and target : 1 to X
        for (int numOfDices = 1; numOfDices <= d ; numOfDices++) {

            for (int target = 1; target <= t ; target++) {

                int ans = 0;
                for (int i = 1 ; i <= faces ; i++) {

                    // (target-i) invalid index ho sakta hain isliye checking it.
                    if ((target - i) >= 0) {

                        // Maine nth dice use kar lia i.e. numOfDices-1
                        ans = ans + dp[numOfDices - 1][target - i];
                    }
                }

                dp[numOfDices][target] = ans;
            }
        }

        return dp[d][t];
    }

    // ##################### Space Optimization  ###################
    /*

       Check karo dp[numOfDices][target] kis par depend kar raha hain.

       dp[numOfDices][target] depends on ans.
       ans depends on dp[numOfDices - 1][target-i]

       i.e. dp[numOfDices][target] depends on it's previous row but we didn't know on which column it is depending
       i.e. (target-i) is changing.

       Row-1 : -----------------------------

       Row-2 : ----------------------------- prev

       Row-3 : ----------------------------- curr

       Row-4 : -----------------------------

       Since , numOfDices goes from 0 to N in the for loop of Tabulation it means each time curr goes down then prev takes place of curr.
       i.e. prev = curr.


       Hence ,

       Row-0 : ----------------------------- prev

       Row-1 : ----------------------------- curr

       Row-2 : -----------------------------

       Row-3 : -----------------------------

       Row-4 : -----------------------------


       i.e. i only need two rows instead of 2D Dp

     */

    public static int solve_so (int d , int faces , int t) {

        // Step-1 :
        int[] prev = new int[t + 1];
        int[] curr = new int[t + 1];

        // Step-2 :
        prev[0] = 1;


        // dice = 0 and target = 0 ke liye hamne already fill kar dia hain upar i.e. dice : 1 to N and target : 1 to X
        for (int numOfDices = 1; numOfDices <= d ; numOfDices++) {

            for (int target = 1; target <= t ; target++) {

                int ans = 0;
                for (int i = 1 ; i <= faces ; i++) {

                    if ((target - i) >= 0) {

                        // Maine nth dice use kar lia i.e. numOfDices-1
                        ans = ans + prev[target - i];
                    }
                }

                curr[target] = ans;
            }

            prev = curr.clone();
        }

        return prev[t];
    }

}
