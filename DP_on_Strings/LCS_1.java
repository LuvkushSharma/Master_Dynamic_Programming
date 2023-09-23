package DP_on_Strings;

/*

Given two strings text1 and text2, return the length of their longest common subsequence. If there is no common subsequence, return 0.
A subsequence of a string is a new string generated from the original string with some characters (can be none) deleted without changing
the relative order of the remaining characters.

For example, "ace" is a subsequence of "abcde".
A common subsequence of two strings is a subsequence that is common to both strings.



Example 1:

Input: text1 = "abcde", text2 = "ace"
Output: 3
Explanation: The longest common subsequence is "ace" and its length is 3.

Example 2:

Input: text1 = "abc", text2 = "abc"
Output: 3
Explanation: The longest common subsequence is "abc" and its length is 3.

Example 3:

Input: text1 = "abc", text2 = "def"
Output: 0
Explanation: There is no such common subsequence, so the result is 0.


Constraints:

1 <= text1.length, text2.length <= 1000
text1 and text2 consist of only lowercase English characters.

 */


/*
               a =  "a  b   c   d   e"
                     ^
                     |
                     i

               b =  "a  c   e"
                     ^
                     |
                     j

              // Match
             if (a[i] == b[j])  {

                 ans = 1 + fn (a , b , i+1 , j+1);

             } else {

                 ans1 = fn (a , b , i , j+1);
                 ans2 = fn (a , b , i+1 , j);

                 ans = max (ans1 , ans2);
             }


 */

import java.util.Arrays;

public class LCS_1 {

    public static void main(String[] args) {

        String a = "abcde";
        String b = "def";

        int ans = solve_recur (a , b , 0 , 0);

        System.out.println(ans);

//        -------------- Using Memo ------------

        int [][]dp = new int[a.length()][b.length()];

        for (int arr[] : dp) {

            Arrays.fill (arr , -1);
        }

        int ans1 = solve_memo (a , b , 0 , 0 , dp);
        System.out.println(ans1);

//        ------------- Using Tabulation --------------
        int ans2 = solve_Tabu (a , b);
        System.out.println(ans2);

//        ------------ Using Space Optimization ----------
        int ans3 = solve_SO (a , b);
        System.out.println(ans3);

    }

    public static int solve_recur (String a , String b , int i , int j) {

        // Base Case
        if (i >= a.length() || j >= b.length()) {

            return 0;
        }

        int ans = 0;

        if (a.charAt(i) == b.charAt(j)) {

            ans = 1 + solve_recur (a , b , i+1 , j+1);

        } else {

            int ans1 = solve_recur (a , b , i , j+1);
            int ans2 = solve_recur (a , b , i+1 , j);

            ans = Math.max (ans1 , ans2);
        }

        return ans;
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

    public static int solve_Tabu (String a , String b) {

        int [][]dp = new int[a.length() + 1][b.length() + 1];

        for (int i = a.length()-1 ; i >= 0 ; i--) {

            for (int j = b.length()-1 ; j >= 0 ; j--) {

                int ans = 0;

                if (a.charAt(i) == b.charAt(j)) {

                    ans = 1 + dp[i+1][j+1];

                } else {

                    int ans1 = dp[i][j+1];
                    int ans2 = dp[i+1][j];

                    ans = Math.max (ans1 , ans2);
                }

                dp[i][j] = ans;
            }
        }

        return dp[0][0];
    }

    // ----------------- Space Optimization -----------------
   // Since , dp curr_row and next_row par hee depend kar raha hain
    // i.e. instead of taking a matrix of size O (a.length * b.length) we will take two 1D arrays
    // i.e. curr_row and next_row

    // SC = O(m)
    public static int solve_SO (String a , String b) {

        int []curr_row = new int[b.length() + 1];
        int []next_row = new int[b.length() + 1];

        // Jahan par bhi (i+1) dikhe dp[][] wahan par next_row use karo and jahan par bhi (i) dikhe dp main then use curr_row.
        // And next hamaara upar jaa raha hain and same with curr. i.e. Agar curr_row = 3 hain then next_row = 3 ho jaayega at the end
        // and curr_row = 2 ho jaayega.

        for (int i = a.length()-1 ; i >= 0 ; i--) {

            for (int j = b.length()-1 ; j >= 0 ; j--) {

                int ans = 0;

                if (a.charAt(i) == b.charAt(j)) {

                    ans = 1 + next_row[j+1];

                } else {

                    int ans1 = curr_row[j+1];
                    int ans2 = next_row[j];

                    ans = Math.max (ans1 , ans2);
                }

                curr_row[j] = ans;
            }

            next_row = curr_row;
        }

        return next_row[0];
    }
}
