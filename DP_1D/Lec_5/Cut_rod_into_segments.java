package DP_1D.Lec_5;

/*

Problem Statement

You are given an integer 'N' denoting the length of the rod. You need to determine the maximum number of segments you can make of this rod provided that each segment should be of the length 'X', 'Y', or 'Z'.

Input Format :

The first line of input contains an integer 'T' denoting the number of test cases.

The next 'T' lines represent the 'T' test cases.

The only line of each test case contains 4 integers denoting 'N' , 'X' , 'Y' , and 'Z', where 'N'
is the length of the rod and 'X' , 'Y' , 'Z' are the segments into which a given rod can be cut into.

Sample Input 1:

2

7 5 2 2

8 3 3 3

Sample Output 1:

2
0

--- Img ---


In the first test case, cut it into 2 parts of 5 and

2.

In the second case, there is no way to cut into segments of 3 length only as the length of the rod is less than the given length.

Sample Input 2:

2

7 3 2 2

8 1 4 4

Sample Output 2:

3
8

Explanation For Sample Input 2:

In the first test case, cut it into 3 parts of 3,2 and 2.

In the second case, cut it into 8 parts of length 1.


 */

/*
                                                        7 [X = 5 , Y = 2 and Z = 2]
                                              X = 5   /     \
                                                     /       \  Y = 2
                                                    /         \
                                     [5,2,2]       2            5 [5,2,2]
                                                 /   \              |   \
                                         Y = 2  /      \  Z = 2     |    \ Z = 3
                                               /         \          |    Same recursive tree as the previous 1
                                              0           0         |
                                            return                  |
                                                                    |
                                                                  /   \
                                                           X=  5/       \  Y = 2
                                                              /          \
                                                            0            3 [5 , 2 , 2]
                                                          return        /  \
                                                                    2  /     \ 2 <--- 2nd 2
                                                                      /        \
                                                                     1 [5,2,2]  1 [5,2,2]
                                                            Kisi kaa bhi          return -INF
                                                            call nhi lag sakta
                                                           Isliye
                                                           return -INF


                        n
                    /   |   \
              x   /    y|     \ z
                /       |      \
             n-x       n-y     n-z


             n-x matlab ----> (n-x) length kii rod ke liye kitne number of segments use kar sakte ho.
             n-y : length kii rod ke liye kitne number of segments aa rahe hain.

             So, ham (n-x) par tabhi aaye hongye jab hamne n main se 'x' segment tod diya hoga. i.e. 1 + solve ()

 */

import java.util.Arrays;

public class Cut_rod_into_segments {

    public static void main(String[] args) {

        int n = 7;
        int x = 5;
        int y = 2;
        int z = 2;

        // ------------------ Recursion ----------------

        int ans1 = solve_recur (n , x , y , z);
        System.out.println(ans1);

        // ----------------- Memoization ---------------

        // Step-1 :
        int []dp = new int[n+1];

        Arrays.fill (dp , -1);
        int ans2 = solve_memo (n , x , y , z , dp);
        System.out.println(ans2);



        // ---------------- Tabulation --------------
        int ans3 = solve_tabu (n , x , y , z);
        System.out.println(ans3);



    }

    // ##################### Recursion ###################

    public static int solve_recur (int n, int x, int y, int z){

        // If rod kii length 0 hain toh ham kitne segments use karengye 0 banaane ke liye.
        if(n == 0){
            return 0;
        }

        if(n < 0){

            return Integer.MIN_VALUE;

        }

        int a = 1 + solve_recur (n-x , x , y , z);
        int b = 1 + solve_recur (n-y , x , y , z);
        int c = 1 + solve_recur (n-z , x , y , z);


        int ans = Math.max(a, Math.max(b,c));

        return ans;

    }

    // ##################### Memoization ###################

    /*

        Since , only variable state is changing i.e. 1D DP.

        dp[i] means i length kii rod kaa maximum number of segments kitne ho sakte hain.

        So, agar mujhe 'n' length kii rod nikaalni hain toh main dp[n] daalunga but , index out of bound ayega i.e. dp[n]

     */

    /*
         TC : O(N)
         SC : O(N) + O(N)

     */
    public static int solve_memo (int n , int x , int y ,int z , int []dp){

        if(n == 0){
            return 0;
        }

        if(n < 0){

            return Integer.MIN_VALUE;
        }

        // Step-3 :
        if(dp[n] != -1) {

            return dp[n];
        }

        int a = solve_memo (n-x , x,y,z , dp) + 1;
        int b = solve_memo (n-y , x ,y,z ,dp) +1;
        int c = solve_memo (n-z , x, y, z ,dp) +1;

        // Step-2
        dp[n]= Math.max(a, Math.max(b,c));

        return dp[n];
    }


    // ##################### Tabulation ###################

    /*
        TC : O(N)
        SC : O(N)

     */

    public static int solve_tabu (int n , int x , int y , int z) {

        // Step-1 :
        int []dp = new int[n+1];

        // Step-2 :
        Arrays.fill (dp , Integer.MIN_VALUE); // base-case-2

        dp[0] = 0; // -------> base case-1


        // 0 to n chalaana thaa but dp[0] = 0 already kar chuke hain isliye no need to do it further.
        for(int i = 1 ; i <= n ; i++){

            if(i-x >= 0)
                dp[i] = Math.max(dp[i] , dp[i-x] + 1) ;

            if(i-y >= 0)
                dp[i] = Math.max(dp[i],dp[i-y] + 1) ;

            if(i-z >= 0)
                dp[i] = Math.max(dp[i],dp[i-z] + 1) ;

        }

        if(dp[n] < 0){
            return 0;
        }

        else{

            return dp[n];

        }
    }


    // ##################### Space Optimization ###################

    /*

    dp[i] depends on dp[i-x] , dp[i-y] and dp[i-z]


     But , yahan par agar (i-1) , (i-2) and (i-3) hota toh mere liye asaan thaa i.e. prev1 , prev2 and prev3
     But , yahan par (i-x) hain i.e. fix nhi hain kii kahan par hoga prev1. Similarly , prev2 and prev3 are also not fixed i.e.

     Space Optimization possible nhi hain.

     */
}
