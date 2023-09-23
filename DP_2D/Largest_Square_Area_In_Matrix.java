/*

LINK : https://practice.geeksforgeeks.org/problems/largest-square-formed-in-a-matrix0806/1

Given a binary matrix mat of size n * m, find out the maximum size square sub-matrix with all 1s.

Example 1:

Input: n = 2, m = 2
mat = {{1, 1},
       {1, 1}}
Output: 2

Explanation: The maximum size of the square
sub-matrix is 2. The matrix itself is the
maximum sized sub-matrix in this case.
Example 2:

Input: n = 2, m = 2
mat = {{0, 0},
       {0, 0}}

Output: 0

Explanation: There is no 1 in the matrix.

Expected Time Complexity: O(n*m)
Expected Auxiliary Space: O(n*m)

Constraints:
1 ≤ n, m ≤ 50
0 ≤ mat[i][j] ≤ 1


 */

/*
     Ex : 1

         {{0, 1},
          {1, 0}} ----> In this case we have two sub-matrix that contains 1's i.e. ans = 1

     Ex : 2

        {{1 , 0 , 1 , 0 , 0},
         {1 , 0 , 1 , 1 , 1},
         {1 , 1 , 1 , 1 , 1},
         {1 , 0 , 0 , 0 , 0}}

         Possible sub-matrices are :-

            2   3 <--- index                          3    4  <---- index
       1    1   1                         Or     1    1    1
       2    1   1                                2    1    1
       ^                                         ^
       |                                         |
     index                                      index


            2 <--- index
            1                   Similarly , we have many square matrix of 1 X 1 on (0,0) , (1,0) , (2,0) , (3,0) ,....


    Hence , ans = 2




    Ex : 3

        {{1 , 1 , 1 , 1},
         {1 , 1 , 1 , 1},
         {1 , 1 , 1 , 1},
         {1 , 1 , 1 , 1}}

      In the above Ex-3 we have many squares.

                                j
                                |
                                V
                          1     1     1     1
                i  -----> 1     1     1     1
                          1     1     1     1
                          1     1     1     1

      Let's start from (0,0) ---> Here we have a square of size-1 now , we ask him kya main iss
                                  1 size ke square ko extend kar sakta huun 2-size ke square main.
                                  Now , the question is extend karne ke liye kya kya cheez chaiye hogyi.
                                  So, value at (i,j) must be a 1 i.e. mat[i][j] = 1 and case-2 is that diagonal value
                                  khud se bhi ek square banaana chaiye. Upper side hain woh bhi saare ke saare 1's hone
                                  chaiye. Case-3 : Left side bhi saare ke saare 1's hone chaiye.

                                  1     1
                                     \  |
                                      \ |
                                  1 --- 1

                                  In short , we can make a square of size-2

     Now , can we make 3-size square matrix.

                                          j
                                          |
                                          V
                         ------------
                          1     1   |     1     1
                          1     1   |     1     1
                         -----------
                                       \
                                        \
               i  ----->  1     1        1     1
                          1     1        1     1

     Case-1 : Sabse pehle diagonal matrix of size-2 kaa element i.e. (i,j) ---> 1 hona chaiye. -----> True
     Case-2 : and then (i,j) waale ke upar jo diagonal hain woh puuri 1 honi chaiye yaa fir square banaani honi chaiye of 1's <---- True

     Case-3 : (i,j) ke upar 2 one's hone chaiye.

     Case-4 : (i,j) ke left main 2 one's hone chaiye.


     Now , can we make 4-size square matrix.


                                                j
                                                |
                                                V
                         --------------------
                          1     1         1 |   1
                          1     1         1 |   1
                          1     1         1 |   1
                         --------------------
                                              \
                                                \
            i  ------>    1     1         1      1


     Case-1 : Sabse pehle diagonal matrix of size-3 kaa element i.e. (i,j) ---> 1 hona chaiye ----> True
     Case-2 : and then (i,j) waale ke upar jo diagonal hain woh puuri 1 honi chaiye yaa fir square banaani honi chaiye of 1's  <--- True

     Case-3 : (i,j) ke upar 3 one's hone chaiye. ------> True

     Case-4 : (i,j) ke left main 3 one's hone chaiye.  -----> True


     Note : int ans = 1 + Math.min (right , Math.min(diagonal , down));

     Abb ye Math.min(....) kyo lagaya hain  ?

     Reason :
                  (1)
               Current_val ---------> 2
               |           \
               |            \
               |             \
               1              3

               Current_val i.e. 1 ke right ne ans laaya kii mere taraf se 2 size kii square matrix ban sakti hain.
               Current_val i.e. 1 ke diagonal ne ans laaya kii mere taraf se 3 size kii square matrix ban sakti hain.
               Current_val i.e. 1 ke down ne ans laaya kii mere taraf se 1 size kii square matrix ban sakti hain.

               i.e.

               1  |  1    1    0
            -------

               1     1    0    0

               0     0    1    0

               0     0    0    1


              So, 1 ke right ne bola thaa kii mere taraf se 2 size  kaa square ban sakta hain similarly diagonal ne bola thaa
              3 size kii matrix kaa and then down ne 1 size kaa hee.

              But , if we see the above matrix then we can conclude that Sirf 2 size kaa hee square matrix ban sakta hain
              by including the curr 1 i.e.

              min (down , diagonal , right) ----> will be the added to the curr dimension to form a new extended matrix.



 */

package DP_2D;

import java.util.Arrays;

public class Largest_Square_Area_In_Matrix {

    static int maxi = 0;

    public static void main(String[] args) {

         maxi = 0;

         int mat[][] = {{1 , 1 , 1 , 1},
                        {1 , 1 , 1 , 1},
                        {1 , 1 , 1 , 1},
                        {1 , 1 , 1 , 1}};

         // ---------- Recursive approach gives TLE ----------
        // solve_recur (mat , 0 , 0);

        // Using Memoization
        // Since , two parameters are changing i.e. 2D Dp
        int dp[][] = new int[mat.length][mat[0].length];

        for (int arr[] : dp) {

            Arrays.fill(arr , -1);
        }

        // solve_memo (mat , 0 , 0 , dp);
        // System.out.println(maxi);

        // TC : O(m * n)
        // SC : O(m * n)
        int ans = solve_Tabu (mat);
        System.out.println(ans);

        // TC : O(m * n)
        // SC : O(n)
        int ans1 = solve_Space_Optimised (mat);
        System.out.println(ans1);

    }

    public static int solve_recur (int mat[][] , int i , int j) {

        // Base Case
        // i and j out of boundary chale gaye.
        if (i >= mat.length || j >= mat[0].length) {

            return 0;
        }

        // Aagr main (0,0) par huun toh i ahve 3 directions to move : Ek right , Ek bottom and ek Diagonal

        int right = solve_recur (mat , i , j+1);
        int diagonal = solve_recur (mat , i+1 , j+1);
        int down = solve_recur (mat , i+1 , j);

        // Jiss element par main abhi khada huun woh bhi toh 1 hona chaiye
        if (mat[i][j] == 1) {

            int ans = 1 + Math.min (right , Math.min(diagonal , down));

            maxi = Math.max (maxi , ans);

            return ans;

        } else {

            return 0;
        }


    }

    // Isme main i = 0 and j = 0 par khada huun and isko main row-1 and col-1 mai par leke jaa raha huun.
    public static int solve_memo (int mat[][] , int i , int j , int dp[][]) {

        // Base Case
        if (i >= mat.length || j >= mat[0].length) {

            return 0;
        }

        if (dp[i][j] != -1) {

            return dp[i][j];
        }


        int right = solve_memo (mat , i , j+1 , dp);
        int diagonal = solve_memo (mat , i+1 , j+1 , dp);
        int down = solve_memo (mat , i+1 , j , dp);

        if (mat[i][j] == 1) {

            dp[i][j] = 1 + Math.min (right , Math.min(diagonal , down));

            maxi = Math.max (maxi , dp[i][j]);

            return dp[i][j];

        } else {

            return dp[i][j] = 0;
        }

    }

    // Bottom Up Approach
    // Isme main i = row-1 and j = col-1 par khada huun and isko main row = 0 and col = 0 par leke jaa raha huun.
    public static int solve_Tabu (int mat[][]) {

        int row = mat.length;
        int col = mat[0].length;

        // Because we are going out bound. Since , we are starting from i = row-1 so, in case of
        // dp[i+1][j] ----> we are going out of bound i.e. ek kaam karo dp ke size ko ek ek se bhada do.
        int dp[][] = new int[row + 1][col + 1];

        // After analysing the base case we get to know that when we are going out of boundary then we have to
        // initialize the dp with 0 but dp is already initialized with 0  so, no need to do that again.

        int output = 0;

        for (int i = row-1 ; i >= 0 ; i--) {

            for (int j = col-1 ; j >= 0 ; j--) {

                int right = dp[i][j+1];
                int diagonal = dp[i+1][j+1];
                int down = dp[i+1][j];

                if (mat[i][j] == 1) {

                    dp[i][j] = 1 + Math.min (right , Math.min(diagonal , down));

                    output = Math.max (output , dp[i][j]);

                } else {

                    dp[i][j] = 0;
                }
            }
        }
        return output;
    }


    /*
       ----------------------- Space Optimization ---------------------

       dp[i][j] kis kis par depend kar raha hain pehle woh laao.

                        dp[i][j+1] ----> same row ke next col par
                    /
                  /
                /
       dp[i][j] -------------> dp[i+1][j+1] i.e. next row ke next col par
               \
                 \
                   \
                     dp[i+1][j] i.e. next row ke same col par

     So, mujhe 2 colmns he dikh raha hain yaa fir toh curr_col or may be the next colm.

     So, mere pass 2 rows kaa mat hota

 Curr row  --->      |   |   |   | ...
                     ---------------------------
 Next row  --->      |   |   |   | ...

 Har ek iteration ke baad curr_row upar jaa raha hain and next_row bhi upar jaa raha hain.

 i.e. agar curr_row : row = 3 par thaa toh woh abb row = 2 par chala jaayega.

 i.e. next = curr and curr ko naye sire se calculate karna suru kar dengye.

     */

    public static int solve_Space_Optimised (int mat[][]) {

        int row = mat.length;
        int col = mat[0].length;

        // So, instead of Using the (m*n) dp array we will use two separate 1D array
        int curr_row[] = new int[col + 1];
        int next_row[] = new int[col + 1];

        // Jahan par dp[i] likha hain woh curr_row hain and jahan par dp[i+1] hain woh next_row hogyi.

        int output = 0;

        for (int i = row-1 ; i >= 0 ; i--) {

            for (int j = col-1 ; j >= 0 ; j--) {

                int right = curr_row[j+1];
                int diagonal = next_row[j+1];
                int down = next_row[j];

                if (mat[i][j] == 1) {

                    curr_row[j] = 1 + Math.min (right , Math.min(diagonal , down));

                    output = Math.max (output , curr_row[j]);

                } else {

                    curr_row[j] = 0;
                }
            }

            next_row = curr_row;

        }
        return output;
    }



}
