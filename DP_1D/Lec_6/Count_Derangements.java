package DP_1D.Lec_6;

/*

Question Link : https://www.codingninjas.com/studio/problems/count-derangements_873861

A Derangement is a permutation of 'N' elements, such that no element appears in its original position.
For example, an instance of derangement of {0, 1, 2, 3} is {2, 3, 1, 0}, because 2 present at index 0 is not at its initial position
which is 2 and similarly for other elements of the sequence.
Given a number 'N', find the total number of derangements possible of a set of 'N' elements.

Input Format:
The first line of input contains an integer 'T' denoting the number of test cases.
The first line and the only line of each test case contains an integer 'N' denoting the number of elements whose derangements
are to be counted.

Output :

Return total number of derangement's possibles.

Sample Input 1:
2
2
3
Sample Output 1:
1
2

Explanation Of Sample Output 1:
In test case 1, For two elements say {0, 1}, there is only one possible derangement {1, 0}. 1 is present at index 0 and not at its actual position, that is, 1. Similarly, 0 is present at index 1 and not at its actual position, that is, 0.

In test case 2, For three elements say {0, 1, 2}, there are two possible derangements {2, 0, 1} and {1, 2, 0}. In both the derangements, no element is present at its actual position.

Sample Input 2:
2
1
4
Sample Output 2:
0
9

Explanation Of Sample Output 2:
In test case 1, For the array = {0}, there is no possible arrangements. Hence, the answer is 0 (zero).

In test case 2, For the array elements = {0, 1, 2, 3}, total 9 arrangements are possible. One of them is: { 3, 2, 1, 0}.

 */

/*

----------------------------- Approach ------------------------------------


            ------------------------------------------------------------------------------
            |  0   |  1    |  2   |   3    |   4   |     |     |  i   |     |     |  n   |
            ------------------------------------------------------------------------------
               0      1      2        3        4                   ith              nth


            0 main kaha par rakh sakta huun : --> I can place 0 from idx = 1 to idx = nth.
            So, hamaare pass (n-1) blocks hain to place '0'.

            (n-1) * [Solution of sub-problem]


            Let's say main iss '0' ko ith index par rakhna chahta huun. i.e. 'i' hatke 0 aa jaayega.

            ------------------------------------------------------------------------------
            |  0   |  1    |  2   |   3    |   4   |     |     |  0   |     |     |  n   |
            ------------------------------------------------------------------------------
               0      1      2        3        4     .........     ith              nth

             Toh isme catch kya hua ?

             Isme catch ye hain kii 'i' uthke 0 kii jagye par chala gaya.

            ------------------------------------------------------------------------------
            |  i   |  1    |  2   |   3    |   4   |     |     |  0   |     |     |  n   |
            ------------------------------------------------------------------------------
               0      1      2        3        4     ..........    ith              nth


            Two possibilities :-

            1️⃣. 'i' and '0' both are swapped.

              Fixed                                              Fixed
               |                                                  |
               V                                                  V
            ------------------------------------------------------------------------------
            |  i   |  1    |  2   |   3    |   4   |     |     |  0   |     |     |  n   |
            ------------------------------------------------------------------------------
               0      1      2        3        4    ..........     ith              nth

            So, 0th index and ith index is fixed so, remaining indexes/blocks are : (n-2)
            and , total numbers remaining are (n-2)

            i.e. we had divided the sub-problem into f(n-2).

            f(n) ----> f(n) kaa matlab mere pass n blocks hain jisme n numbers hain.
            f(n-2) ----> f(n-2) kaa matlab mere pass n-2 blocks hain jisme n-2 numbers hain.


            2️⃣. Maine 'ith' index par '0' toh daal diya but main '0'th index par 'i' ko daalna nhi chahta.

                                                                    Fixed
                                                                      |
                                                                      V
                ------------------------------------------------------------------------------
                |  i   |  1    |  2   |   3    |   4   |     |     |  0   |     |     |  n   |
                ------------------------------------------------------------------------------
                   0      1      2        3        4    ..........    ith               nth

                Main nhi chahta 0th index par 'i' aaye , main nhi chahta 1st index par 1 aaye , main nhi chahta
                kii 2nd index par 2 aaye ,..... then we have (n-1) blocks are left.

                indexes/blocks ----> (n-1)
                numbers -----> (n-1)

                i.e. Maine f(n) ko break kar diyaa sub-problem main : f(n-1)


                Recurrence Relation :

                ---------------------------------------
                |   f(n) = (n-1) * [f(n-2) + f(n-1)]  |
                ---------------------------------------


             Summary : Mujhe ye patah hain kii mere pass '0' ko place karne ke liye (n-1) possibilities hain. Inn (n-1) dabbo main
                       se kahi par bhi daal dunga. Fir ek sub-problem bachegyi i.e. '0' ko toh place kar diyaa but baaki sabhi
                       numbers ko bhi toh place karna hain.

                       i.e. (n-1) * (soln of sub-problem)

                       Abb two cases bante hain :

                       1. Jahan par mera 'i' pada thaa maine usse '0' ke dabbe se swap kar diya. i.e. '0' and 'i' kii posn fixed
                          ho gayi and bacaha part fir (n-2) blocks kaa reh gaya.
                          So, sub-problem iss case main ye banegyi : f(n-2)

                       2. '0' ko ith index par place karne ke baad '0' ko posn fixed ho gayi but main nhi chahta i ko 0 par place karna.
                          i.e. mere pass (n-1) blocks bache abb derangements ke liye.
                          So, sub-problem iss case main ye banegyi : f(n-1)


             Note :

             if (n == 1) ----> means we have only one element for derangement.

             if (n == 2) i.e. array looks like : {0,1} so, there is only one derangement possible. i.e. swap 0 and 1 i.e. {1,0}



 */

import java.util.Arrays;

public class Count_Derangements {

    public static void main(String[] args) {

        // int n = 1;
        int n = 4;
        // int n = 2;
        // int n = 3;


        // --------------- Recursion -----------
        int ans1 = solve_recur (n);
        System.out.println(ans1);


        // -------------- Memoization -----------

        // Step-1
        int []dp = new int[n + 1];
        Arrays.fill (dp , -1);

        int ans2 = solve_memo (n , dp);
        System.out.println(ans2);


        // -------------- Tabulation ------------

        int ans3 = solve_tabu (n);
        System.out.println(ans3);


        // -------------- Space Optimization ----------

        int ans4 = solve_so (n);
        System.out.println(ans4);


    }

    // ######################### Recursion #######################

    // TC : Exponential
    // SC : O(N)
    public static int solve_recur (int n) {

        // Base Case
        if (n == 1)
            return 0;

        if (n == 2)
            return 1;


        int ans = (n - 1) * (solve_recur (n - 2) + solve_recur (n - 1));

        return ans;
    }



    // ######################### Memoization #######################

    // TC : O(N)
    // SC : O(N) + O(N)
    public static int solve_memo (int n , int []dp) {

        // Base Case
        if (n == 1)
            return 0;

        if (n == 2)
            return 1;

        // Step-3
        if (dp[n] != -1) {
            return dp[n];
        }


        int ans = (n - 1) * (solve_memo (n - 2 , dp) + solve_memo (n - 1 , dp));

        // Step-2
        dp[n] = ans;

        return dp[n];
    }



    // ######################### Tabulation #######################

    /*
         In top-down : 'n' goes from n to 1
        So, in bottom-up : 'n' goes from 1 to n
    
     */        

    // TC : O(N)
    // SC : O(N)
    public static int solve_tabu (int n) {

        // Step-1
        int []dp = new int[n+1];

        // Step-2
        dp[1] = 0;
        dp[2] = 1;

        // Since : Constraint is N > 0 i.e. N's range is from 1 to n
        // So, we had already calculated for 1 and 2 so, now we need to calculate derangement's count from 3 to n.

        for (int i = 3 ; i <= n ; i++) {

            int ans = (i - 1) * (dp[i - 2] + dp[i - 1]);
            dp[i] = ans;
        }

        return dp[n];
    }


    // ######################### Space Optimization #######################
    /*

            Kya main isse space optimise kar sakta huun. Iske liye mujhe ye check karna hoga kii
            dp[i] kispar depend kar raha hain.

            So, dp[i] is dependent on ans and ans is dependent on dp[i-1] and dp[i-2]

            i.e. replace dp[i-1] -----> prev1
            and  replace dp[i-2] -----> prev2


            ---------------------------------------------------------------------------------
            |      |       |      |        |       |        |        |        |      |      |
            ---------------------------------------------------------------------------------
               0      1      2        3        4     prev2     prev1    dp[i]            nth

            and prev2 = prev1
                prev1 = ans

     */

    // TC : O(n)
    // SC : O(1)
    public static int solve_so (int n) {

        // Step-1
        int prev2 = 0;
        int prev1 = 1;

        for (int i = 3 ; i <= n ; i++) {

            int ans = (i - 1) * (prev2 + prev1);

            // Moving prev1 and prev2
            prev2 = prev1;
            prev1 = ans;
        }

        return prev1;
    }
}
