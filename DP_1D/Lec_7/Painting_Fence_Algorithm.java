package DP_1D.Lec_7;

/*

Question Link : https://www.codingninjas.com/studio/problems/ninja-and-the-fence_3210208

----------------------------- Ninja And The Fence --------------------------

Ninja has given a fence, and he gave a task to paint this fence. The fence has 'N' posts, and Ninja has 'K' colors.
Ninja wants to paint the fence so that not more than two adjacent posts have the same color.

Ninja wonders how many ways are there to do the above task, so he asked for your help.

Your task is to find the number of ways Ninja can paint the fence. Print the answer modulo 10^9 + 7.

Example:
Input: 'N' = 3, 'K' = 2
Output: 6

Say we have the colors with the numbers 1 and 0. We can paint the fence with 3 posts with the following different combinations.

110
001
101
100
010
011

Input Format :
The first line of input contains an integer 'T', denoting the number of test cases.

Each test case will contain two integers 'N' and 'K' denoting the number of posts on the fence and the number of colors Ninja has.
Output Format :
For each test case, print the number of ways modulo 10^9 + 7 to paint the fence.

Output for each test case will be printed in a separate line.


Sample Input 1 :
2
1 1
3 2
Sample Output 1 :
1
6

Explanation Of Sample Input 1 :
For the first test case, there is only one way to paint the fence.

For the second test case, We can paint the fence with 3 posts with the following different combinations.

110
001
101
100
010
011

Sample Input 2 :
2
2 4
4 2
Sample Output 2 :
16
10


 */

/*

---------------> Not more than two adjacent posts have the same color.

                 R G B B R ----> is this a valid colored posts sequence ✔️

                 But : R R R G B ----> is not a valid colored sequence ❌



                Solve (n , k) ----> means , in how many ways we can paint the 'n' posts with 'k' colors such that not more than
                                    two adjacent posts have same color.


                In below chart :  'X' represents post

       -------------------------------------------------------------------------------------------------------------
               |    n = 2 and k = 3          |       n = 3 and k = 3                  |     n = 4 and k = 3
       --------------------------------------------------------------------------------------------------------------
               |                             |                                        |
               |    X X                      |       X X X                            |
       --------------------------------------------------------------------------------------------------------------
               |                             |  For n = 2 , main different colors     |
               |                             |  used karke jo pattern bana thaa unme  |
         Same  |  RR                         |  last ke do colors same kardo. i.e.    |  ans1 = 18
         Color |  GG                         |  RGG , RBB , GRR , GBB , BRR and BGG   |
         Used  |  BB                         |                                        |
               |                             |                                        |
               |  i.e. for k = 3 , ans1 = 3  |  ans1 = 6                              |
       -------------------------------------------------------------------------------------------------------------
               |                         /   |                                      /  |
               |                        /    |                                     /   |
     Different |  RG                   /     |                                    /    |
     Color     |  RB                  /      |                                   /     |   ans2 = 24 * (k-1) i.e.
     Used      |  GR                 /       |    We have total 9 posts i.e.    /      |   ans2 = 24 * (2) => 48
               |  GB                /        |                                 /       |
               |  BR               /         |    RR                          /        |
               |  BG              6          |    GG                        18         |
               |                             |    BB                                   |
               |  i.e. when different colors |    RG                                   |
               | are used : ans = k * (k-1)  |    RB                                   |
               |  ans2 = 6                   |    GR                                   |
               |                             |    GB                                   |
               |  It's simply a permutation  |    BR                                   |
               |  and combination i.e.       |    BG                                   |
               |                             |                                         |
               |  ----------------           |   Last ke posts kaa color different     |
               |  |  3   |   2   |           |                                         |
               |  ----------------           | RR main last main R laga hua hain i.e.  |
               |    |          |             | (k-1) colors are left i.e. we have (k-1)|
               |    V          v             | choices  more. i.e. RRG and RRB         |
               |  3 colors    2 colors left  | GG ke last main G laga hua hain i.e.    |
               |  now i.e. 2 choices         | we have (k-1) ways more to paint the third posts.
               |  i.e. 3 choices             | k = 3 i.e. k-1 => 2                     |
               |                             | GGB and GGR                             |
               |  Total choices = 3 * 2 ==> 6| Similarly , (k-1) ways for left 6 ways. |
               |                             | Hence , ans2 =  9 * (2) i.e. 18         |
     -------------------------------------------------------------------------------------------------------------------
                                            / |                                        /
     solve(2 , 3) ======> ans = (3 + 6)    /  |                                       / |
     i.e. ans = 9                         /   |                                      /  |
                                         9    |                                     /   |  ans = 18 + 48 ==> 66
     i.e. ans = k + k * (k-1)                 |  ans = k + k*(k-1) ==> 6 + 18 ===> 24   |
     -------------------------------------------------------------------------------------------------------------------




     Let's say : n = 4

     Case1 : Last ke 2 post kaa color same hoga.
     Case2 : Last ke 2 post kaa color different hoga.


     i.e.    X    X    X    X  --> n=4
             /               \
            /                 \
  Same     /                   \ different
         (n-2)

         RG
         RB
         BR
         BG
         GR
         GB

      i.e. I can add
      RR , GG and BB at the last

      But , on RG we can add RR and BB
      and unable to add GG because it will
      form more than 2 same adjacent colors
      i.e. (k-1) choice


      Similarly, on RB i can add RR and GG but not able to
      add BB , hence (k-1) choices.

      solve (n) depends on solve (n-2) * (k-1)  ---------> Ye toh same waale ke liye hua now , talk about different colors also.


      Let's say , n = 3
      X  X  X

      So, we can add any color out of R , G and B

      RGB
      RGG
      RBG
      RBB
      BRG
      BGG
      .
      .
      24


      On , RGB i can add R and G but unable to add B as i want to add different color. i.e. (k-1) colors left
      Similarly , RBB have (k-1) choices.

      i.e. solve(n) = solve (n-1) * (k-1)


      In short : Recursive relation is

      -----------------------------------------------------
      | solve (n) = (k-1) * [solve (n-2) + solve (n-1)]   |
      -----------------------------------------------------
                                  |              |
                                  v              V
                                Same          Different


 */

import java.util.Arrays;

public class Painting_Fence_Algorithm {

    public static void main(String[] args) {

        int n = 4;
        int k = 3;

        // ----------- Recursive -------------
        int ans1 = solve_recur (n , k);
        System.out.println(ans1);


        // ----------- Memoization ------------
        int []dp = new int[n+1];
        Arrays.fill (dp , -1);

        int ans2 = solve_memo (n , k , dp);
        System.out.println(ans2);

        // ----------- Tabulation ------------
        int ans3 = solve_tabu (n , k);
        System.out.println(ans3);

        // ----------- Space Optimization ------------
        int ans4 = solve_so (n , k);
        System.out.println(ans4);

    }


    // ################# Recursion #################

    public static int solve_recur (int n , int k) {

        // Base Case
        if (n == 1)
            return k;

        if (n == 2)
            return add (k , mul (k , k-1));

        int ans = add (mul(solve_recur (n-2 , k) , k-1) , mul(solve_recur(n-1 , k) , k-1));

        return ans;
    }

    // ################# Memoization #################
    /*
         Since , in the recursive call only one variable is changing i.e. 'n' i.e. we had applied 1D DP.

     */

    public static int solve_memo (int n , int k , int []dp) {

        // Base Case
        if (n == 1)
            return k;

        if (n == 2)
            return add (k , mul (k , k-1));

        if (dp[n] != -1)
            return dp[n];

        dp[n] = add (mul(solve_memo (n-2 , k , dp) , k-1) , mul(solve_memo(n-1 , k , dp) , k-1));

        return dp[n];
    }


    // ################# Tabulation #################

    public static int solve_tabu (int n , int k) {

        // Step-1
        int []dp = new int[n+1];

        // Step-2
        dp[1] = k;
        dp[2] = add (k , mul (k , k-1));


        for (int i = 3 ; i <= n ; i++) {

            dp[i] = add (mul(dp[i-2] , k-1) , mul(dp[i-1] , k-1));

        }

        return dp[n];
    }

    // ################# Space Optimization #################

    /*

       Pehle check karo : dp[i] kis par depend kar raha hain :


       dp[i] --------------------> dp[i-2]
             \
              \
               \
                \
                  dp[i-1]


       i.e. dp[i-2] = prev2
       and dp[i-1] = prev1

     */

    public static int solve_so (int n , int k) {

        // Step-1
        int []dp = new int[n+1];

        // Step-2
        int prev2 = k;
        int prev1 = add (k , mul (k , k-1));


        for (int i = 3 ; i <= n ; i++) {

            int ans = add (mul(prev2 , k-1) , mul(prev1 , k-1));

            prev2 = prev1;
            prev1 = ans;

        }

        return prev1;
    }


    public static int add (int a , int b){
        return (a + b);
    }

    public static int mul (int a , int b) {
        return (a * b);
    }
}
