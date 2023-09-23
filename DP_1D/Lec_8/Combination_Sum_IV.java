package DP_1D.Lec_8;

/*

You are given an array of distinct integers and you have to tell how many different ways of selecting the elements from the array are there such that the sum of chosen elements is equal to the target number tar.
Note: Two ways are considered the same if for every index the contents of both the ways are equal example way1=[1,2,3,1] and way2= [1,2,3,1] both way1 and way 2 are the same.
But if way1 =[1,2,3,4] and way2= [4,3,2,1] then both ways are different.
Input Is Given Such That The Answer Will Fit In A 32-Bit
Integer.

For Example:
If N = 3 and tar = 5 and array elements are [1,2,5] then the number of possible ways of making sum = 5
are:
(1,1,1,1,1)
(1,1,1,2)
(1,2,1,1)
(2,1,1,1)
(1,1,2,1)
(2,2,1)
(1,2,2)
(2,1,2)
(5)

Hence the output will be 9.

Sample Input 1 :
2
3 5
1 2 5
2 3
1 2
Sample Output 1 :
9
3
Explanation For Sample Output 1:
For the first test case, the number of possible ways will be
(1,1,1,1,1)
(1,1,1,2)
(1,2,1,1)
(2,1,1,1)
(1,1,2,1)
(2,2,1)
(1,2,2)
(2,1,2)
(5)

For the second test case, the number of ways will be
(1,1,1)
(1,2)
(2,1)
Here you can see we have considered (1,2) and (2,1) in 2 different ways.

Sample Input 2 :
2
3 4
12 1 3
2 41
2 34
Sample Output 2 :
3
0

 */


/*
                                         {1,2} , tar = 3
                                        /     \
                                    1  /        \  2
                                      /           \
                              {1,2} , tar=2      {1,2} , tar = 1 -----------> Already Computed
                             / \                  / \
                        1  /    \  2           1 /    \ 2
                         /        \             /       \
                {1,2}, tar=1   {1,2}, tar=0  {1,2},tar=0 {1,2} , tar = -1 ----> return 0;
              / \
          1 /    \ 2
          /       \
  {1,2},tar=0   {1,2},tar=-1
    |                |
    |                |
    V                V
  return 0        return -1

 */

import java.util.Arrays;

public class Combination_Sum_IV {

    public static void main(String[] args) {

            int []num = {1 , 2 , 5};
            int tar = 5;

            // --------------- Recursion -------------
            int ans = solve_recur (num , tar);
            System.out.println(ans);


           // --------------- Memoization -------------
            // Step-1 : Make a dp array and initialize it with -1.
            int []dp = new int[tar + 1];
            Arrays.fill (dp , -1);

            int ans1 = solve_memo (num , tar , dp);
            System.out.println(ans1);

           // -------------- Tabulation --------------
             int ans2 = solve_tabu (num , tar);
             System.out.println(ans2);

    }

    // ###################### Recursion ###################
    // TC : Exponential
    public static int solve_recur (int []num , int tar) {

        // Base Case
        if (tar < 0) {
            return 0;
        }

        if (tar == 0) {
            return 1;
        }

        int ans = 0;
        for (int i = 0 ; i < num.length ; i++) {

            ans += solve_recur(num , tar - num[i]);
        }

        return ans;
    }

    // ###################### Memoization ###################
    /*
         Hma ekaise patah kii 1D DP hain yaa fir 2D.
         Dhyaan se dekho recursive call main konsa parameter change ho raha hain and kitne ho rahe hain.
         Bro, 2 parameters hain and isme se sirf 1 hee ho raha hain i.e. tar.
         i.e. 1D DP

     */

    // TC : O(target)
    public static int solve_memo (int []num , int tar , int dp[]) {

        // Base Case
        if (tar < 0) {
            return 0;
        }

        if (tar == 0) {
            return 1;
        }

        // Step-3 : Base Case Khatam hone ke baad check karlo answer pada hain ?
        if (dp[tar] != -1) {

            return dp[tar];
        }

        int ans = 0;
        for (int i = 0 ; i < num.length ; i++) {

            ans += solve_memo(num , tar - num[i] , dp);
        }

        // Step-2 :
        dp[tar] = ans;

        return dp[tar];
    }


    // ###################### Tabulation ###################
    /*

        So, dp[0] par toh 1 hee agyega and abb hame i = 1 se lekar i = tar tak loop chalaana hain.
        and dp[tar - num[i]] ho raha hain i.e. mujhe i waale loop ke andarr ek aur loop chalaana padega.
        i.e. for num[]

     */

    // TC : O(tar * nums.length)
    public static int solve_tabu (int []num , int tar) {

        // Step-1 :
        int []dp = new int[tar+1];

        // Step-2: Analyze the base case and fill dp accordingly.
        dp[0] = 1;

        // traversing from target 1 to tar
        for (int i = 1 ; i <= tar ; i++) {

            // Traversing on num array.
            for (int j = 0 ; j < num.length ; j++) {

                // Handling Invalid Index.
                if ((i - num[j]) >= 0) {

                    dp[i] += dp[i - num[j]];
                }
            }
        }

        return dp[tar];
    }

    // #################### Space Optimization #####################
    /*

        Kya main space optimization kar sakta huun ?

        We only need to see that on what variable our dp is dependent.

        so, dp[i] is dependent on dp[i - num[j]]

        Here , we know that dp[i] is dependent on something but we donot know that i is depedent on what ?

            ----------------------------------------------
            |      |       |      |   X    |       |     |
            ----------------------------------------------

            |                      |
            ------------------------
                        |
            num[j] can be any value
            from this range

            i.e. we are not sure about the value of num[j]
     */

}
