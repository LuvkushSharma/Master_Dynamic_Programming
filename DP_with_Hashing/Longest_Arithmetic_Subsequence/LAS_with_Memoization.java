package DP_with_Hashing.Longest_Arithmetic_Subsequence;

import java.util.HashMap;

public class LAS_with_Memoization {

    public static void main(String[] args) {

        int []arr = {1 , 7 , 10 , 13 , 14 , 19};
        int n = arr.length;

        int ans = lengthOfLongestAP (arr , n);
        System.out.println(ans);
    }

    // Backward checkup
    // top down memoization
    // tc : O(n^3)
    // sc : O(n^2)
    public static int solve_memo (int i , int diff , int []arr , HashMap<Integer , Integer> []dp) {

        // Base Case
        if (i < 0) {
            return 0;
        }

        // Kya dp[i] ke andarr iss difference kaa answer exists karta hain Agar haan toh usse return kardo.
        if (dp[i].containsKey(diff)) {
            return dp[i].get(diff);
        }

        int ans = 0;

        for (int j = i-1 ; j >= 0 ; j--) {

            if (arr[i] - arr[j] == diff) {

                ans = Math.max (ans , 1 + solve_memo (j , diff , arr , dp));
            }
        }

        dp[i].put (diff , ans);

        return ans;
    }

    public static int lengthOfLongestAP (int []arr , int n) {

        if (n <= 2)
            return n;

        int ans = 0;

        // dp[i] contains a hashmap and that hashmap contains diff and length of longest AP corresponding to that diff.
        HashMap<Integer, Integer>[] dp = new HashMap[n + 1];

        // Initializing dp
        for (int i = 0; i < dp.length; i++) {
            dp[i] = new HashMap<>();
        }

        for (int i = 0 ; i < n ; i++) {

            for (int j = i+1 ; j < n ; j++) {

                /*
                   Since , yaha par 'i' kii value change ho rahi hain
                   and diff bhi change ho raha hain i.e. arr[j] - arr[i].

                   i.e. 2D DP use hoga.

                   dp[i][diff] ---> ye kya batata hain.
                       |
                       v
                   Till this index i.e. till 'ith' index iss difference (diff) ke saath length of longest AP kya hain ?

                   Where can go upto : length of the array.

                   But , har ek index par different-different common difference ho sakte hain. So, ek index par multiple
                   common difference (d) ho sakte hain i.e. d1 , d2 , d3 ,...... and har 'd' ke liye longest length hogyi
                   i.e. len1 with d1 , len2 with d2 , len3 with d3 ,...


                   In short , i will make a HashMap<Integer , Integer> dp[len+1]
                   i.e. We have multiple indexes and har index par hamne ek map bana rakha hain Jo kii diff ke saath uski
                   length store kar raha hain.

                 */
                ans = Math.max (ans , 2 + solve_memo (i , arr[j] - arr[i] , arr , dp));
            }
        }

        return ans;
    }
}
