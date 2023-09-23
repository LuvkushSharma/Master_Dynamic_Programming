package DP_with_Distinct_Ways.Partition_Equal_Subset_Sum;

/*

Given an array arr[] of size N, check if it can be partitioned into two parts such that the sum of elements in both parts is the same.

Example 1:

Input: N = 4
arr = {1, 5, 11, 5}
Output: YES
Explanation:
The two parts are {1, 5, 5} and {11}.

Example 2:

Input: N = 3
arr = {1, 3, 5}
Output: NO
Explanation: This array can never be
partitioned into two such parts.


Expected Time Complexity: O(N*sum of elements)
Expected Auxiliary Space: O(N*sum of elements)

Constraints:
1 ≤ N ≤ 100
1 ≤ arr[i] ≤ 1000
N*sum of elements ≤ 5*106


 */

/*

Ex-1 :
arr[] = { 1  , 5  , 11 , 5}

It can be partitioned into : {1 , 5 , 5} and {11}


Ex-2 :
arr[] = {1 , 3 , 5}

So, we cannot partitioned this array into two subsets such their are sum are equal.


---------------------------------- Approach -----------------------------------------

    1st Observation :

     Input :  {................}
                    |
                    |
                    -----> Total sum of this input can be even or can be odd.
                           So, even sum ko ham divide karke two separate subsets main kar dengye.

                           But kya main odd sum ko 2 subsets main divide kar sakta huun. Let's say total_sum = 23 toh kya 23/2 ===> 11.5 ke 2 subsets bana sakte hain jab sabhi
                           elements array main integer hain.

                           Hence , if (total_sum % 2 != 0) {

                                       return 0; // i.e. unable to make two subsets.
                                   }


     2nd Observation :

                      Now , let's say total_sum = 22 so, 22/2 --> 11 <-- target_sum

                      Toh maine problem ko todd diya. Kya main ek aisa subset bana sakta huun jiska sum mere target_sum ke equal aa jaaye.  So, hamne question change kar dia.
                      Question said kii kya main iss array main se 2 subset bana sakta huun jinka total sum equal aaye. And , hamne isse aisa kar diya kii kya main iss array
                      main se ek aisa subset nikaal sakta huun jiska sum mere target_sum ke equal aaye.

                      where : target is total_sum/2


     Ex : arr -> {1 , 5 , 11 , 5}

     so, total_sum = 22

     So, condn-1 : Is total_sum is even ?
                   Yes.

         condn-2 : target_sum = 22/2 ==> 11
                   kya array main aisa subset hain jiska sum 11 ho.

                   So, problem ban gayi : {1 , 5 , 11 , 5} and target = 11 , sum = 0

                                                               Step-1 : { 1    ,    5    ,    11    ,    5}
                                                                          i
                                                                       /     \
                                                        Include       /       \     Exclude
                                                   {1,5,11,5} , 11 , 1
                                                      i
                                                    /  \
                                        INC        /    \  EXC
                                                  /      \
                               {1,5,11,5} , 11 , 6  <---------------- (false || true) ==> return true
                                     i
                                   /   \
                      INC         /     \ EXC
                                 /       \
                {1,5,11,5} , 11 , 17   {1,5,11,5} , 11 , 6    <--------- true || false ==> true upar jaayega.
                                  |            i
                                  |           /  \
                                  V          /    ------------------------> {1,5,11,5} , 11 , 6
       Ye toh galat ho gaya              INC/                                         i
       So, this gonna be the base case.   {1,5,11,5} , 11 , 11                return false
       Yahan se baapis laut jaao.                   i     |
                                                          |
       return false                                       V
                                                   Base Case bhi reach ho gaya
                                                   and sum bhi equal ho gaya.

                                                   return true;


        ⚠️
        Note : Instead of taking sum we will decrement the target and when target == 0 then we got the subset having sum equal to the target_sum.


 */

import java.util.Arrays;

public class Partition_Equal_Subset_Sum {

    public static void main(String[] args) {

        int []arr = {1 , 5 , 11 , 5};
        int N = arr.length;

        int total = 0;
        for (int i = 0 ; i < N ; i++) {

            total += arr[i];
        }

        // Odd Number
        if (total % 2 != 0) {

            System.out.println("NO");

        } else {

            int target = total/2;

            // ---------------- Recursion ----------------
            boolean ans1 = solve_recur (arr , N , 0 , target);
            System.out.println(ans1);


            // --------------- Memoization --------------
            int [][]dp = new int [N][target + 1];

            for (int a[] : dp) {
                Arrays.fill (a , -1);
            }

            boolean ans2 = solve_memo (arr , N , 0 , target , dp);
            System.out.println(ans2);



            // -------------- Tabulation ---------------

            boolean ans3 = solve_tabu (arr , N , target);
            System.out.println(ans3);


            // -------------- Space Optimization --------------

            int ans4 = solve_so (arr , N , target);

            if (ans4 == 1) {

                System.out.println("YES");

            } else {

                System.out.println("NO");
            }
        }

    }

    // ################### Recursion ##################

    public static boolean solve_recur (int []arr , int N , int idx , int target) {

           // Base Case
           if (idx >= N) {
               return false;
           }

           if (target < 0) {
               return false;
           }

           if (target == 0) {
               return true;
           }

           boolean include = solve_recur (arr , N , idx + 1 , target - arr[idx]);
           boolean exclude = solve_recur (arr , N , idx + 1 , target - 0);

           return include || exclude;
    }

    // ################### Memoization ##################

    /*

          Recursive call main kon kon se parameters change ho rahe hain ?

          So, out of 4 parameters : 2 are changing i.e. idx and target

          i.e. 2D DP

     */

    public static boolean solve_memo (int []arr , int N , int idx , int target , int [][]dp) {

        // Base Case
        if (idx >= N) {
            return false;
        }

        if (target < 0) {
            return false;
        }

        if (target == 0) {
            return true;
        }

        if (dp[idx][target] != -1) {

            return dp[idx][target] == 1;
        }

        boolean include = solve_memo (arr , N , idx + 1 , target - arr[idx] , dp);
        boolean exclude = solve_memo (arr , N , idx + 1 , target - 0 , dp);

        boolean ans = include || exclude;

        dp[idx][target] = ans ? 1 : 0;

        return ans;
    }


    // ################### Tabulation ##################

    /*

         Top-down main : idx goes from 0 to N
                         target goes from total/2 to 0

         Bottom-up : Just reverse above initializations

                     idx goes from N to 0
                     target goes from 0 to total/2

     */

    public static boolean solve_tabu (int []arr , int N , int t) {

        // Step-1 :
        int [][]dp = new int [N + 1][t + 1];

        // Step-2 : Analyze the base case and fill the dp accordingly.
        // Jitne bhi base case main 0 return ho raha hain unhe toh dekhne kii lead hee nhi becoz dp already 0 se initialized hain.
        // if (target == 0) then return 1 --------> i.e. when column = 0 then ans = 1;

        for (int row = 0 ; row <= N ; row++) {

            dp[row][0] = 1;
        }

        // Since , after analyzing the first base case we get to know that when idx >= N then return 0
        // and dp[N][..] is already filled with 0 so, start from N-1 instead of N

        for (int idx = N-1 ; idx >= 0 ; idx--) {

            for (int target = 0 ; target <= t ; target++) {

                boolean include = false;

                if ((target - arr[idx]) >= 0) {

                    include = dp[idx + 1][target - arr[idx]] == 1;
                }

                boolean exclude = dp[idx + 1][target] == 1;

                dp[idx][target] = include || exclude ? 1 : 0;
            }
        }


        return dp[0][t] == 1;
    }


    // ################### Space Optimization ##################

    /*

        dp[idx][target] depends on include and exclude
        include depends on : dp[idx + 1][target - arr[idx]]
        exclude depends on : dp[idx + 1][target]


        dp[idx][target] ----------------------> dp[idx + 1][target - arr[idx]]
                       \
                        \
                         \
                          \
                            dp[idx + 1][target]


       So, let's say    dp[idx][target] = currRow
       then ,           dp[idx + 1][...] = nextRow



       Row start ho rahi hain (N-1) row se


       ------------------------------------ (n-1)th row -----------> currRow

       ------------------------------------  nth row  --------------> nextRow


       and in the next iteration :

       ------------------------------------ (n-3)rd row

       ------------------------------------ (n-2)th row  --------------> currRow

       ------------------------------------ (n-1)th row -----------> nextRow

       ------------------------------------  nth row

       nextRow = currRow

     */

    public static int solve_so (int []arr , int N , int t) {

        int []currRow = new int[t + 1];
        int []nextRow = new int[t + 1];

        // dono ke pehle colm main 1 daal diya.
        currRow[0] = 1;
        nextRow[0] = 1;

        for (int idx = N-1 ; idx >= 0 ; idx--) {

            for (int target = 0 ; target <= t ; target++) {

                int include = 0;

                if ((target - arr[idx]) >= 0) {

                    include = nextRow[target - arr[idx]];
                }

                int exclude = nextRow[target];

                int ans = 0;

                if (include == 1 || exclude == 1)
                    ans = 1;

                currRow[target] = ans;
            }

            nextRow = currRow;
        }

        return nextRow[t];
    }

}
