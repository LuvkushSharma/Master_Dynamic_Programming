package DP_with_Hashing.Longest_Arithmetic_Subsequence;

import java.util.HashMap;

public class LAS_with_Tabulation {


    public static void main(String[] args) {

        int []arr = {1 , 7 , 10 , 13 , 14 , 19};
        int n = arr.length;

        int ans = lengthOfLongestAP (arr);
        System.out.println(ans);
    }

    // Backward checkup
    // bottom up tabulation
    // tc : O(n^2)
    // sc : O(n^2)
    public static int lengthOfLongestAP (int[] nums) {

        int n = nums.length;

        int ans = 0;

        if (n <= 2) {
            return n;
        }

        HashMap<Integer, Integer>[] dp = new HashMap[n];
        for (int i = 0; i < n; i++) {

            dp[i] = new HashMap<>();

        }

        // abb 'j' hamaara aagye move karega and 'i' fixed rahega.
        // i.e. i=2 par 'j' pehle 0 index ke liye check karega then index = 1 ke liye.
        // i.e. we are doing reverse work as we had done in the top down approach.

        // Why we had started 'i' from 1 not from 0.
        // Beacuse agar i = 0 se start karunga toh i and j dono same element ko point karengye jo kii possible nhi hain.
        // Kya ek element se AP main 2 kaam karta hain ? --> Nope
        for (int i = 1; i < n; i++) {

            for (int j = 0; j < i; j++) {

                // Yaha par 'i' hamesha agye hoga i.e. nums[i] - nums[j]
                int diff = nums[i] - nums[j];

                // Abhi tak apki AP main kitne elemnt pade hai woh count (cnt) bataata hain.
                // i.e. 'i' toh AP main ayega hee ayegaa. But , 'j' bhi toh aa sakta hain. But 'j' tabhi ayega jab diff match karega.
                int cnt = 1;

                /*

                ---> 'j' ko check karo kii 'j' par iss difference ke saath answer pada hua hain kya.
                ---> Agar pada hua hain toh dp[i][diff] ko update kardo.
                ---> i.e. jo bhi mere dp[i][diff] main answer pada hua hain usse update kardo by +1

                      dp[j].getOrDefault(diff, cnt) + 1

                      Because hamne 0 to j tak kaa answer toh nikaal diya but 'ith' element ko bhi toh AP ke andarr include
                      karna hain. i.e. +1

                 */

                // Check if answer already present
                dp[i].put(diff, dp[j].getOrDefault(diff, cnt) + 1);

                ans = Math.max(ans, dp[i].get(diff));
            }
        }

        return ans;
    }
}
