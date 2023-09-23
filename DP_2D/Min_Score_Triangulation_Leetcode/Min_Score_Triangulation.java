package DP_2D.Min_Score_Triangulation_Leetcode;

/*

You have a convex n-sided polygon where each vertex has an integer value. You are given an integer array values where values[i] is the value of the ith vertex (i.e., clockwise order).

You will triangulate the polygon into n - 2 triangles. For each triangle, the value of that triangle is the product of the
values of its vertices, and the total score of the triangulation is the sum of these values over all n - 2 triangles in
the triangulation.

Return the smallest possible total score that you can achieve with some triangulation of the polygon.



Example 1:
                       2
                       /\
                     /    \
                   /        \
                 /            \
                ----------------
              1                   3

Input: values = [1,2,3]
Output: 6
Explanation: The polygon is already triangulated, and the score of the only triangle is 6.

Example 2:


Input: values = [3,7,4,5]
Output: 144
Explanation: There are two triangulations, with possible scores: 3*7*5 + 4*5*7 = 245, or 3*4*5 + 3*4*7 = 144.
The minimum score is 144.

Example 3:


Input: values = [1,3,1,4,1,5]
Output: 13
Explanation: The minimum score triangulation has score 1*1*3 + 1*1*4 + 1*1*5 + 1*1*1 = 13.


Constraints:

n == values.length
3 <= n <= 50
1 <= values[i] <= 100


 */

/*


                       2
                       /\
                     /    \
                   /        \
                 /            \
                ----------------
              1                   3

              Iss polygon par iss prakaar line daalni hain i.e. division karne hain ke saare ke saare parts triangle ban jaaye.

              Above polygon is already a triangle so, return : 1*2*3 ===> 6


              ---------------------------
              |                          |
              |                          |
              |                          |
              |                          |
              ----------------------------

              If i put a line diagonally in above polygon then we can get a triangle. i.e.


              see Rectangle.jpg

              see Pentagon.jpg

              see Hexagon.jpg


              So, Recurrence relation : (a X b X f) + solve (i , k) + solve (k , j)

              i.e. ek triangle bana lo baaki recursion sambhaal lega.


              -------- Base Case --------


              .---------------.
              a               b
              i               j

              (i+1) == j then it means total number of nodes are 2



 */

import java.util.Arrays;

public class Min_Score_Triangulation {

    public static void main(String[] args) {

        int values[] = {1 , 2 , 3};
        int n = values.length;

        // ----------- Recursion ----------
        int ans = solve (values , 0 , n-1);
        System.out.println(ans);

        // ----------- Memoization ----------
        int dp[][] = new int[n][n];

        for (int arr[] : dp) {

            Arrays.fill (arr , -1);
        }

        int ans1 = solve_Memo (values , 0 , n-1 , dp);
        System.out.println(ans1);


        // ---------- Bottom Up --------------
        int ans2 = solve_Tabu (values);
        System.out.println(ans2);
    }

    // ------------------ Recursive Approach -------------
    public static int solve (int v[] , int i , int j) {

        // Base Case
        if ((i + 1) == j) {

            return 0;
        }

        int ans = Integer.MAX_VALUE;

        // We know that for 'k' we have choices from (i+1) to (j-1)
        // i.e. in case of hexagon we can choose k as [b , c , d , e]
        for (int k = (i+1) ; k < j ; k++) {

            int a = v[i];
            int b = v[j];
            int c = v[k];

            ans = Math.min (ans , (a * b * c) + solve (v , i , k) + solve (v , k , j));
        }

        return ans;
    }

    // ---------------- Memoization ------------------
    // Since , two parameters are changing i.e. we will apply 2D DP.
    public static int solve_Memo (int v[] , int i , int j , int dp[][]) {

        // Base Case
        if ((i + 1) == j) {

            return 0;
        }

        if (dp[i][j] != -1) {

            return dp[i][j];
        }

        int ans = Integer.MAX_VALUE;

        // We know that for 'k' we have choices from (i+1) to (j-1)
        // i.e. in case of hexagon we can choose k as [b , c , d , e]
        for (int k = (i+1) ; k < j ; k++) {

            int a = v[i];
            int b = v[j];
            int c = v[k];

            ans = Math.min (ans , (a * b * c) + solve_Memo (v , i , k , dp) + solve_Memo (v , k , j , dp));
        }

        return dp[i][j] = ans;
    }



    // --------------- Bottom Up ---------------------
    // Top Down main hamne i = 0 and j = n-1 se suru kiya thaa and i ko aagye le jaane kii kosis kari thii and j ko peeche.
    // And Bottom up main ulta karengye.
    public static int solve_Tabu (int v[]) {

        int n = v.length;

        int dp[][] = new int[n][n];

        // Agar main j ko i se start karu toh koi sense nhi banta as  single point par dono point kar rahe hain isse koi triangle nhi banega.
        // if j = i+1 -----> Base Condition.
        // i.e. j = i+2 ---> means we have three nodes and unse triangle banaayengye.
        for (int i = n-1 ; i >= 0 ; i--) {

            for (int j = i+2; j < n ; j++) {

                int ans = Integer.MAX_VALUE;

                for (int k = (i+1) ; k < j ; k++) {

                    int a = v[i];
                    int b = v[j];
                    int c = v[k];

                    ans = Math.min (ans , (a * b * c) + dp[i][k] + dp[k][j]);
                }

                dp[i][j] = ans;
            }
        }

        return dp[0][n-1];
    }

    // --------------------- Space Optimization -----------------
    // dp[i][j] kis par dependent hain ---> ans
    // ans mera dp[i][k] and dp[k][j]

    /*

         dp[i][j] -----------------> dp[i][k] (means same row main hain but colm j to k ho gaya hain)
                 \
                   \
                     \
                       \
                         dp[k][j] (means row change ho gayi but colm same hain.)

           and hamaari 'k' kii value traverse bhi kar rahi hain from (i+1) to j
           toh iss case main colm bhi traverse karengye , row bhi traverse karegyi.
           So, We are unable to Space optimization.
     */
}
