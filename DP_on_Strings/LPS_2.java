package DP_on_Strings;

// Longest Palindromic Subsequence

/*

 s = "bbbab"
 LPS in s are : "bbb" , "bab"

 s = "bbbab"
 rev_s = "babbb"

 so, LCS in s and rev_s is bab , bbb  and bbbb

 So, if i find the Longest Common Subsequence of s and rev_s

 ans = 4


 */

import java.util.Arrays;

public class LPS_2 {

    public static void main(String[] args) {

        String s = "bbbab";

        StringBuilder str = new StringBuilder();

        str.append(s);

        str.reverse();

        String s1 = str.toString();

        int [][]dp = new int[s.length()][s1.length()];

        for (int arr[] : dp) {

            Arrays.fill (arr , -1);
        }

        int ans = solve_memo (s , s1 , 0 , 0 , dp);

        System.out.println(ans);

    }

    public static int solve_memo (String a , String b , int i , int j , int [][]dp) {

        // Base Case
        if (i >= a.length() || j >= b.length()) {

            return 0;
        }

        if (dp[i][j] != -1) {

            return dp[i][j];
        }

        int ans = 0;
        if (a.charAt(i) == b.charAt(j)) {

            ans = 1 + solve_memo (a , b , i+1 , j+1 , dp);

        } else {

            int ans1 = solve_memo (a , b , i , j+1 , dp);
            int ans2 = solve_memo (a , b , i+1 , j , dp);

            ans = Math.max (ans1 , ans2);
        }

        return dp[i][j] = ans;
    }
}
