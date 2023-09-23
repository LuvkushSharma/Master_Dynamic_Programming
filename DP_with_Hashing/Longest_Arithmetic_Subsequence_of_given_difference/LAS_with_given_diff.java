package DP_with_Hashing.Longest_Arithmetic_Subsequence_of_given_difference;

/*

Given an integer array arr and an integer difference, return the length of the longest subsequence in arr which is an arithmetic
sequence such that the difference between adjacent elements in the subsequence equals difference.

A subsequence is a sequence that can be derived from arr by deleting some or no elements without changing the order of the
remaining elements.



Example 1:

Input: arr = [1,2,3,4], difference = 1
Output: 4
Explanation: The longest arithmetic subsequence is [1,2,3,4].

Example 2:

Input: arr = [1,3,5,7], difference = 1
Output: 1
Explanation: The longest arithmetic subsequence is any single element.

Example 3:

Input: arr = [1,5,7,8,5,3,4,2,1], difference = -2
Output: 4
Explanation: The longest arithmetic subsequence is [7,5,3,1].


Constraints:

1 <= arr.length <= 105
-104 <= arr[i], difference <= 104



 */

/*

Input-1 :  {1 , 2 , 3 , 4 , 5}  d = 1

1 and 2 main diff = 1 so, AP = {1,2}
2 and 3 main diff = 1 so, AP = {1,2,3}
3 and 4 main diff = 1 so, AP = {1,2,3,4}
4 and 5 main diff = 1 so, AP = {1,2,3,4,5}


Input-2 :  {1 , 3 , 5 , 7}  d = 1

1 and 3 main diff = 2 i.e. not equal to 1
1 and 5 main diff = 4 i.e. not equal to 1
1 and 7 main diff = 6 i.e. not equal to 1

3 and 5 main diff = 2 i.e. not equal to 1
3 and 7 main diff = 4 i.e. not equal to 1

5 and 7 main diff = 2 i.e. not equal to 1

Hence , we can add a single element in AP i.e.
{1} , {3} , {5} , {7}  ----------> but all these AP's are of length '1' i.e. ans = 1



Imp :-
Input-3
{   1     ,    5    ,   7    ,   8    ,   5    ,   3   ,    4   ,   2   ,  1  }  and d = -2

In Tabulation :
Hamne pichle question main ith index waale element ko include kiya and then jth index already answer exists karta hain ?
Agar karta hain toh mujhe uska answer de do main usme +1 (becoz ith index element is also included) karke answer nikaal dunga.


{   1     ,    5    ,   7    ,   8    ,   5    ,   3   ,    4   ,   2   ,  1  }
    |
    V
ans = 1 + 0 <--- means ith index par thaa 1 toh main 1 se bolunga bhai tumhe toh main include kar lunga. kya uske peeche koi element exists krta hain ?


{   1     ,    5    ,   7    ,   8    ,   5    ,   3   ,    4   ,   2   ,  1  }
               |
               V
            ans = 1 + 0 <--- 5 se bolungaa bhai tumhe toh main include kar lunga. Kya peech aisa element exists karta hain jo tumhaari AP main
            aa sakta hain. i.e. 5 - (-2) i.e. 7 toh peeche 7 exists karta thaa kya. ---> Nhi
            So, ans = 1 + 0

{   1     ,    5    ,   7    ,   8    ,   5    ,   3   ,    4   ,   2   ,  1  }
                        |
                        V
                    ans = 1 + 0 <--- 7 se bolungaa bhai tumhe toh main include kar lunga. Kya peech aisa element exists karta hain jo tumhaari AP main
                    aa sakta hain. i.e. 7 - (-2) i.e. 9 toh peeche 9 exists karta thaa kya. ---> Nhi
                    So, ans = 1 + 0

{   1     ,    5    ,   7    ,   8    ,   5    ,   3   ,    4   ,   2   ,  1  }
                                 |
                                 V
                            ans = 1 + 0 <--- 8 se bolungaa bhai tumhe toh main include kar lunga. Kya peech aisa element exists karta hain jo tumhaari AP main
                            aa sakta hain. i.e. 8 - (-2) i.e. 10 toh peeche 10 exists karta thaa kya. ---> Nhi
                            So, ans = 1 + 0

{   1     ,    5    ,   7    ,   8    ,   5    ,   3   ,    4   ,   2   ,  1  }
                                          |
                                          V
                                    ans = 1 + 1 <--- 5 se bolungaa bhai tumhe toh main include kar lunga. Kya peech aisa element exists karta hain jo tumhaari AP main
                                    aa sakta hain. i.e. 5 - (-2) i.e. 7.
                                    So, i = 0 to i = 3 <--- piche waale part main 7 exists karta hain. So, 7 kaa answer + 1 kardo.
                                    i.e. ans = 1 + 1 ===> 2

{   1     ,    5    ,   7    ,   8    ,   5    ,   3   ,    4   ,   2   ,  1  }
                                                   |
                                                   V
                                            ans = 1 + 2 <--- 3 se bolungaa bhai tumhe toh main include kar lunga. Kya peech aisa element exists karta hain jo tumhaari AP main
                                            aa sakta hain. i.e. 3 - (-2) i.e. 5.
                                            So, i = 0 to i = 4 <--- piche waale part main 5 exists karta hain. So, 5 kaa answer + 1 kardo.
                                            i.e. ans = 1 + 2 ===> 3


{   1     ,    5    ,   7    ,   8    ,   5    ,   3   ,    4   ,   2   ,  1  }
                                                            |
                                                            V
                                                    ans = 1 + 0 <--- 4 se bolungaa bhai tumhe toh main include kar lunga. Kya peech aisa element exists karta hain jo tumhaari AP main
                                                    aa sakta hain. i.e. 4 - (-2) i.e. 6.
                                                    So, i = 0 to i = 5 <--- piche waale part main 6 exists nhi karta hain.
                                                    i.e. ans = 1 + 0 ===> 1

{   1     ,    5    ,   7    ,   8    ,   5    ,   3   ,    4   ,   2   ,  1  }
                                                                    |
                                                                    V
                                                            ans = 1 + 1 <--- 2 se bolungaa bhai tumhe toh main include kar lunga. Kya peech aisa element exists karta hain jo tumhaari AP main
                                                            aa sakta hain. i.e. 2 - (-2) i.e. 4.
                                                            So, i = 0 to i = 6 <--- piche waale part main 4 exists karta hain.
                                                            i.e. ans = 1 + answer of 4 i.e. ans = 1 + 1 ===> 2
{   1     ,    5    ,   7    ,   8    ,   5    ,   3   ,    4   ,   2   ,  1  }
                                                                           |
                                                                           V
                                                                    ans = 1 + 3 <--- 1 se bolungaa bhai tumhe toh main include kar lunga. Kya peech aisa element exists karta hain jo tumhaari AP main
                                                                    aa sakta hain. i.e. 1 - (-2) i.e. 3.
                                                                    So, i = 0 to i = 7 <--- piche waale part main 3 exists karta hain. ---> Yes
                                                                    i.e. ans = 1 + answer of 3 i.e. ans = 1 + 3 ===> 4


Inn sabka maximum hamaara answer hoga.

 */

import java.util.HashMap;

public class LAS_with_given_diff {

    public static void main(String[] args) {

            int []arr = {1,5,7,8,5,3,4,2,1};
            int n = arr.length;

            int difference = -2;

            HashMap<Integer, Integer> dp = new HashMap<>();

            int ans = 0;

            for (int i = 0 ; i < n ; i++) {

                // i.e. curr_element - diff
                int temp = arr[i] - difference;
                int tempAns = 0;

                // Check kii temp peeche exists karta hain kii nhi
                // Agar exists karta hain then 1 + (peeche waale kaa answer).
                // If not exists in the map then ans = 1 + (0)

                // Check answer exists for temp already or not
                if (dp.get(temp) != null) {

                    // Currently ham jahan khade hain usse peeche waale element kaa naswer nikaal liya
                    // jo kii AP bana raha hain curr_element se.
                    tempAns = dp.get(temp);
                }

                // current answer ko update kardo
                dp.put(arr[i] , 1 + tempAns);

                // ans update:
                ans = Math.max (ans , dp.get(arr[i]));

            }

        System.out.println(ans);
    }
}
