package Extra_Questions;

/*

Given an input string (s) and a pattern (p), implement wildcard pattern matching with support for '?' and '*' where:

'?' Matches any single character.
'*' Matches any sequence of characters (including the empty sequence).
The matching should cover the entire input string (not partial).



Example 1:

Input: s = "aa", p = "a"
Output: false
Explanation: "a" does not match the entire string "aa".

Example 2:

Input: s = "aa", p = "*"
Output: true
Explanation: '*' matches any sequence.

Example 3:

Input: s = "cb", p = "?a"
Output: false
Explanation: '?' matches 'c', but the second letter is 'a', which does not match 'b'.


Constraints:

0 <= s.length, p.length <= 2000
s contains only lowercase English letters.
p contains only lowercase English letters, '?' or '*'.



 */

/*

str = "abcde"
pattern = "a*c?e"

* kaa matlab : "" , a , aa , aaa , aac , abcd , bcda , *c , *a , *d , *aa , *.........

i.e. '*' can replace that much strings.


Ex-1 :
str = "abcde"
pattern = "a*c?e"

pattern ke 'e' se str kaa 'e' cancel ,
pattern ke '?' se str ke kisi ek character ko replace kar sakte hain so, to cancel with 'd' we will replace ? with d
pattern ke 'c' se str kaa 'c' cancel ,
pattern ke '*' se str kaa 'b' cancel ,
pattern ke 'a' se str kaa 'a' cancel ,

Hence , pattern is a valid string.


Ex-2 :

str = "aa"
pattern = "a"

so, pattern ke 'a' se str kaa last 'a' cancel. But abb pattern khatam ho gaya and str ke andarr abhi bhi ek character bacaha hain.
i.e. pattern is a invalid string.


Ex-3 :

str = "abc"
pattern = "*"

'*' ko main kitne bhi number of characters se replac ekar sakta huun. So , i had replaced '*' with "abc"

Hence , pattern ke "abc" se str kaa "abc cancelled.


Ex-4 :

str = "cb"
pattern = "?a"

agar mujhe c ko cancel karna hain then pattern main '?' ko 'c' se replace karna hoga. But 'b' and 'a' kaise replace hongye.
So, this is also a invalid pattern.

 */

/*

-----------------------   Approach -----------------------


str = "a  b  c   d   e"
                     |
                     V
                     i

pattern = "a  *  c  ?  e"
                       |
                       V
                       j

    Match Conditions :
       1. If str[i] == pattern[j]

       2. if pattern[j] == '?' <--- we can replace '?' with the str[i] so, that we cancel both of them.


    When both str[i] and pattern[j] matches then (i+1) and (j+1)

    i.e. if (match) {

             return fn (str , pattern , i - 1 , j - 1);

         } else if (pattern[j] == '*'){

             ---> Ek baar '*' ko empty string bana and usse compare kara lo
             ---> Aur ek baar


             Ex : str : a  b  c  d  e
              pattern : a  *  c  d  e

              So, c , d and e are already cancelled

              Now , '*' comes so, two cases arises :
              1. Replace '*' with empty string. i.e. a c d e
                 So, in-short mera 'j' waala pointer 'a' par aa gaya and 'i' waala 'b' par hee reh gaya
                 i.e. fn (str , pattern , i , j-1)

              2. str = a  b  c  d  e
             pattern = a  *  c  d  e

             Replace '*' with '*b'

             so, str = a   b   c   d   e
             pattern = a * b   c   d   e

             so, now : 'b' of str matches with the 'b' of pattern.
             Now , 'i' will be on 'a' and 'j' will be on '*'

             i.e. fn (str , pattern , i-1 , j)

             Inn dono main se jo bhi output ayega uska || (Or) karke return kardo

             Hence ,

             return fn (str , pattern , i , j-1)  || fn (str , pattern , i-1 , j)


         } else {


             Ex : str = a   b   c   d
              pattern = b   b   c   d

              b , c and d matches with the str characters but, 'b' of pattern not matches with the 'a' of str.

              Hence , return false

         }


 */

import java.util.Arrays;

public

class Wild_Card_Matching {

    public static void main(String[] args) {

        String str = "abcde";
        String pattern = "a*c?e";

        // ------------- Recursion -----------

        boolean ans1 = solve_recur (str , pattern , str.length() - 1 , pattern.length() - 1);
        System.out.println(ans1);

        // ------------- Memoization -----------

        int [][]dp = new int[str.length()][pattern.length()];

        for (int []arr : dp)
            Arrays.fill (arr , -1);

        boolean ans2 = solve_memo (str , pattern , str.length() - 1 , pattern.length() - 1 , dp);
        System.out.println(ans2);

        // ------------- Memoization Version-2 ('1' Base Indexing) -----------

        int [][]dp1 = new int[str.length() + 1][pattern.length() + 1];

        for (int []arr : dp1)
            Arrays.fill (arr , -1);

        boolean ans3 = solve_memo_V_2 (str , pattern , str.length(), pattern.length(), dp1);
        System.out.println(ans2);

        // ------------- Tabulation -----------
        boolean ans4 = solve_tabu (str , pattern);
        System.out.println(ans4);

        // ------------- Space Optimisation ----------
        boolean ans5 = solve_so (str , pattern);
        System.out.println(ans5);

    }

    // ###################### Recursion #################
    public static boolean solve_recur ( String str , String pattern , int i , int j) {

        // Base Case

        // Dono string consume ho gayi hain tabhi ye base case sahi hain.
        // i.e. return true becoz pattern string is the valid string.
        if (i < 0 && j < 0)
            return true;

        // String puuri consume nhi hui hain but pattern consume ho gaya hain.
        // Ex : str = "abcde"  and pattern = "c?e"
        if (i >= 0 && j < 0)
            return false;

        // Ex : str = "abc" and pattern = "babc"  <--- In the end we are left with the character 'b' i.e. invalid string.

        // str = "abc" and pattern = "*abc"  <---- In this case 'abc' of the pattern will be cancelled with the 'abc' of str. But at the end
        // '*' remains and we will replace '*' with an empty string. So, it's valid pattern.
        // So, end main pattern ke '*' mila toh toh valid pattern hain else nhi.
        if (i < 0 && j >= 0) {

            for (int k = 0 ; k <= j ; k++) {

                // Agar pattern main '*' ke alaawaa kuch bhi milta hain toh return false.
                if (pattern.charAt(k) != '*') {
                    return false;
                }
            }

            return true;
        }


        // Character matches
        if ((str.charAt(i) == pattern.charAt(j)) || pattern.charAt(j) == '?') {

            return solve_recur (str , pattern , i - 1 , j - 1);

        } else if (pattern.charAt(j) == '*') {

            return (solve_recur (str , pattern , i - 1 , j) || solve_recur (str , pattern , i , j - 1));

        } else {

            return false;
        }

    }


    // ###################### Memoization #################

    /*

       Since , in the recursive call two parameters are changing i.e. 'i' and 'j'

     */

    public static boolean solve_memo ( String str , String pattern , int i , int j , int [][]dp) {

        // Base Case

        if (i < 0 && j < 0)
            return true;

        if (i >= 0 && j < 0)
            return false;

        if (i < 0 && j >= 0) {

            for (int k = 0 ; k <= j ; k++) {

                if (pattern.charAt(k) != '*') {
                    return false;
                }
            }

            return true;
        }

        if (dp[i][j] != -1) {

            return dp[i][j] == 1;
        }


        // Character matches
        if ((str.charAt(i) == pattern.charAt(j)) || pattern.charAt(j) == '?') {

            boolean ans = solve_memo (str , pattern , i - 1 , j - 1 , dp);
            dp[i][j] = ans ? 1 : 0;
            return ans;

        } else if (pattern.charAt(j) == '*') {

            boolean ans = (solve_memo (str , pattern , i - 1 , j , dp) || solve_memo (str , pattern , i , j - 1 , dp));
            dp[i][j] = ans ? 1 : 0;
            return ans;

        } else {

            dp[i][j] = 0;
            return false;
        }

    }

    // Since , In the tabulation we need to handle those base cases where '<' signs are used becoz in case of analysing the base case we use these base cases.
    // So, instead of '0' base indexing we'll do '1' base indexing so, that base case modifies to :
    // if (i == 0 && j == 0)

    // $$$$$$$$$$$$$$$$$$$$$$$$ Memoization Using '1' base indexing $$$$$$$$$$$$$$$$

    public static boolean solve_memo_V_2 ( String str , String pattern , int i , int j , int [][]dp) {

        // Base Case

        if (i == 0 && j == 0)
            return true;

        if (i > 0 && j == 0)
            return false;

        if (i == 0 && j > 0) {

            for (int k = 1 ; k <= j ; k++) {

                if (pattern.charAt(k-1) != '*') {
                    return false;
                }
            }

            return true;
        }

        if (dp[i][j] != -1) {

            return dp[i][j] == 1;
        }


        // Character matches
        if ((str.charAt(i-1) == pattern.charAt(j-1)) || pattern.charAt(j-1) == '?') {

            boolean ans = solve_memo_V_2 (str , pattern , i - 1 , j - 1 , dp);
            dp[i][j] = ans ? 1 : 0;
            return ans;

        } else if (pattern.charAt(j-1) == '*') {

            boolean ans = (solve_memo_V_2 (str , pattern , i - 1 , j , dp) || solve_memo_V_2 (str , pattern , i , j - 1 , dp));
            dp[i][j] = ans ? 1 : 0;
            return ans;

        } else {

            dp[i][j] = 0;
            return false;
        }

    }

    // ######################### Tabulation ####################

    // After Converting the memoization to Version_2 Now , we can easily convert Top down to Bottom up.

    /*
           In Top-Down : 'i' goes from str.length()  to 0
                         'j' goes from pattern.length() to 0

           So, In Bottom-Up : 'i' goes from 0 to str.length()
                              'j' goes from 0 to pattern.length()

     */

    public static boolean solve_tabu (String str , String pattern) {

        // Step-1 :
        boolean [][]dp = new boolean[str.length() + 1][pattern.length() + 1];

        // Step-2 : Analyzing the base case
        dp[0][0] = true;

        for (int j = 1 ; j <= pattern.length() ; j++) {

            boolean flag = true;
            for (int k = 1 ; k <= j ; k++) {

                if (pattern.charAt(k-1) != '*') {
                    flag = false;
                    break;
                }
            }

            dp[0][j] = flag;
        }


        // Step-3 :

        for (int i = 1 ; i <= str.length() ; i++) {

            for (int j = 1 ; j <= pattern.length() ; j++) {

                if ((str.charAt(i-1) == pattern.charAt(j-1)) || pattern.charAt(j-1) == '?') {

                    dp[i][j] = dp[i - 1][j - 1];


                } else if (pattern.charAt(j-1) == '*') {

                    dp[i][j] = dp[i - 1][j] || dp[i][j - 1];

                } else {

                    dp[i][j] = false;

                }
            }
        }

       return dp[str.length()][pattern.length()];

    }

    // ###################### Space Optimization #################

    /*
                          dp[i-1][j-1]
                        /
                      /
                    /
                  /
         dp[i][j] ----------------> dp[i-1][j]
                  \
                    \
                      \
                        \
                          dp[i][j-1]



     dp[i-1]  ---------------------------------- row-0   <------- prevRow

     dp[i]    ---------------------------------- row-1   <------- currRow

              ---------------------------------- row-2

               After each iteration : prevRow = currRow

     */

    public static boolean solve_so (String str , String pattern) {

        boolean []prevRow = new boolean[pattern.length() + 1];
        boolean []currRow = new boolean[pattern.length() + 1];

        // Step-2 : Analyzing the base case
        prevRow[0] = true;  // ---> '0'th row is the prevRow

        for (int j = 1 ; j <= pattern.length() ; j++) {

            boolean flag = true;
            for (int k = 1 ; k <= j ; k++) {

                if (pattern.charAt(k-1) != '*') {
                    flag = false;
                    break;
                }
            }

            prevRow[j] = flag;
        }


        // Step-3 :

        for (int i = 1 ; i <= str.length() ; i++) {

            for (int j = 1 ; j <= pattern.length() ; j++) {

                if ((str.charAt(i-1) == pattern.charAt(j-1)) || pattern.charAt(j-1) == '?') {

                    currRow[j] = prevRow[j - 1];


                } else if (pattern.charAt(j-1) == '*') {

                    currRow[j] = prevRow[j] || currRow[j - 1];

                } else {

                    currRow[j] = false;

                }
            }

            prevRow = currRow.clone();
        }

        return prevRow[pattern.length()];

    }

}
