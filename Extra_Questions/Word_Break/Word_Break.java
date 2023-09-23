package Extra_Questions.Word_Break;

/*

Given a string s and a dictionary of strings wordDict, return true if s can be segmented into a space-separated sequence of one or more dictionary words.

Note that the same word in the dictionary may be reused multiple times in the segmentation.



Example 1:

Input: s = "leetcode", wordDict = ["leet","code"]
Output: true
Explanation: Return true because "leetcode" can be segmented as "leet code".

Example 2:

Input: s = "applepenapple", wordDict = ["apple","pen"]
Output: true
Explanation: Return true because "applepenapple" can be segmented as "apple pen apple".
Note that you are allowed to reuse a dictionary word.

Example 3:

Input: s = "catsandog", wordDict = ["cats","dog","sand","and","cat"]
Output: false


Constraints:

1 <= s.length <= 300
1 <= wordDict.length <= 1000
1 <= wordDict[i].length <= 20
s and wordDict[i] consist of only lowercase English letters.
All the strings of wordDict are unique.



 */


/*

s = "mypenmy  and wordDict = {"my" , "pen"}

So, we will partition at each partition point. So, what are the possible partition points : After each character we have a partition point.

              |   |   |   |   |   |
            m | y | p | e | n | m | y
              |   |   |   |   |   |

            Let's partition from :
              |
            m | ypenmy
              |
        word1    ques

        Since , we had segmented the string into word1 and ques so, word1 should be present in the wordDict[]. ----> 'm' not present in the dict.
           |
        my | penmy
           |
      word1   ques

      Now , word1 is present in the wordDict.


      Similarly , we get three words that are found in the wordDict.

      w1    w2      w3
      my |  pen  |  my | ""
                          ^
                          |
                         Whenever we are getting the empty string then return true.

     So, partitioning into a valid string if we get the empty string then it means all the words on the left-hand side are creating
     valid string.

 */

import java.util.Arrays;
import java.util.HashSet;

public class Word_Break {

    public static void main(String[] args) {

        String s = "applepenapple";
        String []wordDict = {"apple","pen"};

        // ------------------ Recursion ----------------

        // Storing wordDict in the set because we need to constantly check the words in the set.
        HashSet<String> set = new HashSet<>();

        for (int i = 0 ; i < wordDict.length ; i++) {

            set.add(wordDict[i]);
        }

        boolean ans1 = solve_recur (s , set , 0);
        System.out.println(ans1);

        // ------------------ Memoization -------------------
        int dp[] = new int[s.length() + 1];

        Arrays.fill (dp , -1);

        boolean ans2 = solve_memo (s , set , 0 , dp);
        System.out.println(ans2);


    }

    // ####################### Recursion ######################

    public static boolean solve_recur (String s , HashSet<String> set , int idx) {

        // Base Case

        // if we had reached to the end of the string then it means all the words are valid.
        if (idx == s.length())
            return true;

        for (int i = idx ; i < s.length() ; i++) {

            String word = s.substring (idx , i  + 1);

            // if word found in the set then check the remaining part of the string.
            // We know that we need to handle only one part and the rest part will be handled by the recursion.
            if (set.contains (word) && solve_recur (s , set , i + 1)) {

                return true;
            }
        }

        return false;
    }

    // ################### Memoization #################

    public static boolean solve_memo (String s , HashSet<String> set , int idx , int []dp) {

        // Base Case

        if (idx == s.length())
            return true;

        // Step-3
        if (dp[idx] != -1) {

            return dp[idx] == 1;
        }

        for (int i = idx ; i < s.length() ; i++) {

            String word = s.substring (idx , i + 1);
            if (set.contains (word)) {

                boolean ans = solve_memo (s , set , i + 1 , dp);

                if (ans) {

                    dp[idx] = 1;
                    return ans;
                }
            }
        }

        dp[idx] = 0;

        return false;
    }
}
