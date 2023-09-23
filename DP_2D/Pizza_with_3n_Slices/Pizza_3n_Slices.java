package DP_2D.Pizza_with_3n_Slices;

/*

There is a pizza with 3n slices of varying size, you and your friends will take slices of pizza as follows:

You will pick any pizza slice.
Your friend Alice will pick the next slice in the anti-clockwise direction of your pick.
Your friend Bob will pick the next slice in the clockwise direction of your pick.
Repeat until there are no more slices of pizzas.
Given an integer array slices that represent the sizes of the pizza slices in a clockwise direction, return the maximum possible sum of slice sizes that you can pick.



Example 1:

img.png

Input: slices = [1,2,3,4,5,6]
Output: 10
Explanation: Pick pizza slice of size 4, Alice and Bob will pick slices with size 3 and 5 respectively. Then Pick slices with size 6, finally Alice and Bob will pick slice of size 2 and 1 respectively. Total = 4 + 6.

Example 2:

img_1.png

Input: slices = [8,9,8,6,1,1]
Output: 16
Explanation: Pick pizza slice of size 8 in each turn. If you pick slice with size 9 your partners will pick slices of size 8.


Constraints:

3 * n == slices.length
1 <= slices.length <= 500
1 <= slices[i] <= 1000



 */

/*

  --------------------> This question is much similar to the House Robbery Question


            -----------------------------------------------
            |  R    |       |      |       |       |      |
            -----------------------------------------------
               ^
               |
            Agar maine
            iss house main robbery kar li
            toh main last waale house main
            robbery nhi kar paunga. Because clockwise dirtn main aa raha hain woh.

            So, baaki recursion fir main index = 1 to n-2 tak lagaunga. i.e.

            -----------------------------------------------
            |       |       |      |       |       |      |
            -----------------------------------------------
                    |                              |
                    --------------------------------
                                    |
                                    V
                     In this range i will rob next time.

          And , agar maine last house main chori kar lii then main 1st house main chorri nhi kar paunga.

          ----------------------------------------------------------------------------------------------

            Let's say i have 3 slices then You ke case main 1 hee slice ayegyi.
            Similarly , agar 9 slices hui toh You ko 3 slices milengyi.

            i.e. Agar 3n slices hain toh :
            Alice get : n slice
            You get : n slice
            Bob gets : n slice.

            So, my goal is to eat n slices.

          slices --> [1 , 2 , 3 , 4 , 5 , 6]

          Let's say above slice array kaa size 'k' hain then i (You) will eat k/3 slice.

          Note :
          if i considering to eat slice at i = 0 then i = slice.length-1 waala bhuul jaao. i.e. processing from 1 to n-2 will be done.
          and if i donot consider to eat slice at i = 0 then processing from i = 1 to n will be done.

          Dono  main se jo max value dega woh answer hoga.



 */


import java.util.Arrays;

// So, main (YOU) kon kon sii slices eat kar sakta hain unko return karna hain but we need to maximize the slice size
// eaten by us (You)
public class Pizza_3n_Slices {

    public static void main(String[] args) {

//        int []slices = {1 , 2 , 3 , 4 , 5 , 6};
        int []slices = {9 , 5 , 1 , 7 , 8 , 4 , 4 , 5 , 5 , 8 , 7 , 7};
        int k = slices.length;

        // --------------- Recursion -------------
        // You will eat k/3 slices out of k slices
        /*

        int case1 = solve_recur (0 , slices , k/3 , k - 2); // -----> Maine 0 index waali slice khaa lii
        int case2 = solve_recur (1 , slices , k/3 , k - 1); // -----> Maine 0 index waali slice nhi khaai

        int ans = Math.max (case1 , case2);
        System.out.println(ans);

         */


        // --------------- Memoization -------------
        /*
        // Step-1 :
        int [][]dp1 = new int[k][k];

        for (int arr[] : dp1) {

            Arrays.fill (arr , -1);

        }

        int [][]dp2 = new int[k][k];

        for (int arr[] : dp2) {

            Arrays.fill (arr , -1);

        }

        // What we are doing is that using the same dp in both the cases which will give wrong answer.
        // So, we need to pass the different dp's
        int case1 = solve_memo (0 , slices , k/3 , k - 2 , dp1); // -----> Maine 0 index waali slice khaa lii
        int case2 = solve_memo (1 , slices , k/3 , k - 1 , dp2); // -----> Maine 0 index waali slice nhi khaai

        int ans1 = Math.max (case1 , case2);
        System.out.println(ans1);


         */

        // -------------- Tabulation --------------
        int ans2 = solve_tabu (slices);
        System.out.println(ans2);

        // ----------------- Space Optimization --------------
        int ans3 = solve_so (slices);
        System.out.println(ans3);

    }

    // ###################### Recursion ###################
    // n is the total number of slices that we gonna eat.
    public static int solve_recur (int index , int []slices , int n , int endIndex) {

         // Base Case
        // case-1 : Mujhe jitni slice khaani thii woh maine khaa lii

        /*
           Case-2 : ending index

                0        1      2      3        4      6
            -----------------------------------------------
            |   R    |       |      |       |       |     |
            -----------------------------------------------

            Agar main 1st house ko le leta huun then mujhe patah hain kii mujhe 6th index waale slice ko consider tak nhi karna.
            i.e. endingIndex = 5 in this case.

            i.e. 0 ke case main mujhe ending index : n-1 bhejna hain naa kii n


         */

         // Iss case main ham '0' waale index kii slice khaate hain then ye ending index n-1 le raha hain jo kii hame consider hee nhi karna.
         // Isliye this is wrong.
         // if (n == 0 || index >= slices.length) {

         if (n == 0 || index > endIndex){
             return 0;
         }

         // Agar maain current slice ko khaa raha huun i.e. index waali slice ko
         // Agar maine index waali slice khaa lii hain toh main index + 1 waali slice nhi khaa sakta because usse Bob khaayega.
         // index + 2 bheja hain.
         int take = slices[index] + solve_recur (index + 2, slices , n - 1 , endIndex);
         int notTake = 0 + solve_recur (index + 1 , slices , n , endIndex);

         return Math.max (take , notTake);
    }

    // ###################### Memoization ###################
    /*
       1D DP hain yaa fir 2D ?

       Recursive call main kitne parameters hain and kitne change ho rahe hain.
       So, total_parameters = 4 and out of 4 ----> 2 are changing i.e. index and n

       i.e. 2D DP

     */
    public static int solve_memo (int index , int []slices , int n , int endIndex , int [][]dp) {

        // Base Case
        if (n == 0 || index > endIndex){
            return 0;
        }

        if (dp[index][n] != -1) {

            return dp[index][n];
        }

        int take = slices[index] + solve_memo (index + 2, slices , n - 1 , endIndex , dp);
        int notTake = solve_memo (index + 1 , slices , n , endIndex , dp);

        dp[index][n] = Math.max (take , notTake);

        return dp[index][n];
    }


    // ###################### Tabulation ###################

    // ------------> Top Down main aisa ho raha thaa
    // Case-1 : index goes from 0 to k-2 and  n goes from k/3 to 0
    // Case-2 : index goes from 1 to k-1 and n goes from k/3 to 0

    // -----------> Bottom Up
    // Upar waali values kaa ulta kar do i.e.
    // Case-1 : main index goes from k-2 to 0 and n goes from 0 to k/3
    // Case-2 : main index goes from k-1 to 0 and n goes from 0 to k/3

    public static int solve_tabu (int []slices) {

        int  k = slices.length;

        // int [][]dp1 = new int[k][k]; ------> Safety ke liye : because index = k-2 par jab ham dp[index + 2] kar rahe hain
        // then we are going index out of bound i.e. safety ke liye k + 2 kar diya.
        int [][]dp1 = new int[k+2][k];

        // Step-2 : Analyze the base case
        // dp[0] = 0 hain jo kii already hain i.e. no need to do extra work.

        for (int index = k - 2 ; index >= 0 ; index--) {

            // n = 0 ke liye already filled hain : done in base case analysis
            for (int n = 1 ; n <= k/3 ; n++) {

                // index = k-2 hain and ham dp1[index+2] kar rahe hain i.e. index out of bound
                int take = slices[index] + dp1[index + 2][n - 1];
                int notTake = dp1[index + 1][n];

                dp1[index][n] = Math.max (take , notTake);

            }
        }

        // Main 0th index tak gaya and k/3 slice khaa lii
        int case1 = dp1[0][k/3];

        // ---------------- Case-2 Handling -----------------
        int [][]dp2 = new int[k+2][k];

        // Step-2 : Analyze the base case
        // dp[0] = 0 hain jo kii already hain i.e. no need to do extra work.

        for (int index = k - 1 ; index >= 1 ; index--) {

            // n = 0 ke liye already filled hain : done in base case analysis
            for (int n = 1 ; n <= k/3 ; n++) {

                int take = slices[index] + dp2[index + 2][n - 1];
                int notTake = dp2[index + 1][n];

                dp2[index][n] = Math.max (take , notTake);

            }
        }

        int case2 = dp2[1][k/3];

        return Math.max (case1 , case2);

    }

    // ###############################  Space Optimization ########################
    /*

          dp1[index][n] depends on dp1[index + 2][n-1]  and dp1[index + 1][n]

          So, dp1 mera 2 rows aagye and 1 row aggye kisi par depend kar raha hain.

          So, dp1[index][n] is prev
              dp1[index + 1][n] is curr
              dp1[index + 2][n - 1] is next

        and same applies to dp2[][]



         prev ------------------

         curr ------------------

         next ------------------


         so, agli iteration main next = curr and curr = prev

     */


    public static int solve_so (int []slices) {

        int  k = slices.length;

        int []prevRow1 = new int[k+2];
        int []currRow1 = new int[k+2];
        int []nextRow1 = new int[k+2];

        int []prevRow2 = new int[k+2];
        int []currRow2 = new int[k+2];
        int []nextRow2 = new int[k+2];

        for (int index = k - 2 ; index >= 0 ; index--) {

            for (int n = 1 ; n <= k/3 ; n++) {

                // index = k-2 hain and ham dp1[index+2] kar rahe hain i.e. index out of bound
                int take = slices[index] + nextRow1[n - 1];
                int notTake = 0 + currRow1[n];

                // Since i am changing values in the prevRow1[] array
                // And , in the below code what i am doing is that currRow1 = prevRow1 i.e. currRow1 and prevRow1 both are pointing to the same memory location
                // so, if i made changes in the prevRow1 then those changes will also occur in the prevRow1.
                // That is why we need to create a deep copy of prevRow1 so, that both the entity can work independently.
                prevRow1[n] = Math.max (take , notTake);

            }


            nextRow1 = currRow1;
            currRow1 = prevRow1.clone();

        }

        // Main 0th index tak gaya and k/3 slice khaa lii
        int case1 = currRow1[k/3];

        // ---------------- Case-2 Handling -----------------

        for (int index = k - 1 ; index >= 1 ; index--) {

            // n = 0 ke liye already filled hain : done in base case analysis
            for (int n = 1 ; n <= k/3 ; n++) {

                int take = slices[index] + nextRow2[n - 1];
                int notTake = 0 + currRow2[n];

                prevRow2[n] = Math.max (take , notTake);

            }

            nextRow2 = currRow2;
            currRow2 = prevRow2.clone();
        }

        int case2 = currRow2[k/3];

        return Math.max (case1 , case2);

    }


}
