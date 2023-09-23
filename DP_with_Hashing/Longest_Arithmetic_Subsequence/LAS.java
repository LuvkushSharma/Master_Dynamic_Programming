package DP_with_Hashing.Longest_Arithmetic_Subsequence;

/*

Given an array called A[] of sorted integers having no duplicates, find the length of the Longest Arithmetic Progression (LLAP) in it.


Example 1:

Input:
N = 6
set[] = {1, 7, 10, 13, 14, 19}
Output: 4
Explanation: The longest arithmetic
progression is {1, 7, 13, 19}.

Example 2:

Input:
N = 5
A[] = {2, 4, 6, 8, 10}
Output: 5
Explanation: The whole set is in AP.

Expected Time Complexity: O(N2)
Expected Auxiliary Space: O(N2)

Constraints:
1 ≤ N ≤ 1000
1 ≤ set[i] ≤ 104

 */

/*

-------------------------- Question Understanding ---------------------

AP :  a , ad+d , a+2d , a+3d ,.... an

where : a is the first term and d is the common difference.

Ex-1 : { 1 , 7 , 10 , 13 , 14 , 19}

We have seral AP's like : {1,7} , {1,10} , {1,13} , {1,14} , {1,19} , ....... but the longest one is the below subsequence.

Longest AP found in above example is : {1 , 7 , 13 , 19} i.e. ans = 4

Ex-2 : {2 , 4 , 6 , 8 , 10}

Here , in the above example whole array is forming AP.
i.e. ans = 5

*/

/*

---------------------------------- Approach -----------------------------

# Brute Force :
                           0       1        2       3        4       5
                        ---------------------------------------------------
                        |   1  |   7    |   10  |  13    |  14    |  19   | <----- arr
                        ---------------------------------------------------


                        a , b , c , d
                        |   |   |   |
                        -----
                          |
                          V
                         diff  = 3

                         So, we only need to find the diff for two numbers baaki recursion sambhaal lega.
                         But , diff toh bahut saare ho sakte hain i.e.
                         7 and 1 ke beech kaa diff : 6
                         10 and 1 ke beech kaa diff : 9
                         12 and 1 ke beech kaa diff : 11
                         13 and 1 ke beech kaa diff : 12
                         14 and 1 ke beech kaa diff : 13
                         10 and 7 ke beech kaa diff : 3
                         13 and 10 ke beech kaa diff : 3
                         14 and 7 ke beech kaa diff : 7
                         14 and 13 ke beech kaa diff : 1
                         .
                         .
                         .
                         .

                         i.e. Total yahan par n^2 ----> Pairs exists karte hain.


                         Brute force kehta hain : Saari AP's find karlo and unme se max waali return kardo.

                         So, i will choose 2 elements so, such that will be the last element of the AP.
                         Like : {14 , 19} and inke peeche aur nhi lag sakte.

                                {13 , 19} having diff = 6 , now inke peeche 7 bhi aa sakta hain i.e. {7 , 13 , 19}
                                and inke peeche 1 bhi aa sakta hain i.e. {1 , 7 , 13 , 19} ---> here d = 6

                                --> We can also take elements from the front also.

                                So, 2 number choose karke main maanunga kii woh AP ke last 2 numbers hain and peeche ke taraf
                                traverse karte hue aur numbers check karta chalunga.

                                Agar apka mann hain kii main starting ke 2 numbers leluu pehle then right kii taraf traverse
                                karu toh woh bhi shi hain.

                        Note : Why we are choosing two numbers not one :
                               Because ek number kaa diff toh nhi nikalta isliye we are taking two numbers instead of one.


                    ---------------------------------------------------------------------------------------------------


                         Pass - 1️⃣ :


                               0       1        2       3        4       5
                            ---------------------------------------------------
                            |   1  |   7    |   10  |  13    |  14    |  19   | <----- arr
                            ---------------------------------------------------
                               i       j

                            Step-1 : Mujhe 2 numbers choose karne hain let's say woh {1 , 7} ho gaye. And maine maan liya kii meri
                            AP ke last two elements hain and abb mujhe inke peeche find out karna hain.

                            i.e. main iss 'i' ke peeche find out karne chala and mujhe dikha yaha par toh element hee nhi hain.
                            i.e. curr_AP => 2

                            Now , j+1

                            Step-2 : 'i' on 1 and 'j' on 10. Main fir 'i' ke peeche jaane laga but elements hee nhi hain.
                                      i.e. AP => {1 , 10} , ans = 2

                                      And j+1.

                                      Similarly , main {1,13} , {1,14} , {1,19} ke liye bhi jaaunga and sabke liye mera ans = 2
                                      ayega becoz 'i' ke peeche koi element exists nhi karta.

                         Pass - 2️⃣ :

                               0       1        2       3        4       5
                            ---------------------------------------------------
                            |   1  |   7    |   10  |  13    |  14    |  19   | <----- arr
                            ---------------------------------------------------
                                       i        j

                            Step-1 : Now , 'i' is on 7 and 'j' is on 10 i.e. 'j' hamesha 'i' se ek aagye se start hoga.
                                     Curr_API : {7 , 10}
                                     Maine 'i' ko peeche kiya but 1 and 7 kaa diff is not equal to 7,10 and 1 ke further peeche jaane
                                     laga but uske peeche koi number hee nhi.
                                     i.e. iss case main ans = 2 i.e. {7,10}


                                    Now , j+1

                            Step-2 : 'i' on 7 and 'j' on 13. Curr API : {7 , 13}  and d = 6
                                      Main fir 'i' ke peeche jaane laga and {1,7} kaa diff = 6 which is equal to the diff of {7,13}
                                      Hence 1 is added to the end of the array. And main further 1 ke peeche nhi jaa sakta.

                                      i.e. AP => {1 , 7 , 13} , ans = 3

                                      And j+1.

                            Step-3 : 'i' on 7 and 'j' on 14. Curr API : {7 , 14}  and d = 7
                                      Main fir 'i' ke peeche jaane laga and {1,7} kaa diff = 6 which is not equal to the diff of
                                      {7,14}. And main further 1 ke peeche nhi jaa sakta.

                                      i.e. AP => {7 , 14} , ans = 3 <--- it remains as previous i.e. not updated.

                                      And j+1.

                            Step-4 : 'i' on 7 and 'j' on 19. Curr API : {7 , 19}  and d = 12
                                      Main fir 'i' ke peeche jaane laga and {1,7} kaa diff = 6 which is not equal to the diff of
                                      {7,19}. And main further 1 ke peeche nhi jaa sakta.

                                      i.e. AP => {7 , 19} , ans = 3 <--- it remains as previous i.e. not updated.

                                      And j+1.

                           Similarly , we will do Pass-3️⃣ where 'i' on 10 and 'j' starts from 13


                        Pass - 4️⃣ :

                               0       1        2       3        4       5
                            ---------------------------------------------------
                            |   1  |   7    |   10  |  13    |  14    |  19   | <----- arr
                            ---------------------------------------------------
                                                       i        j

                            Step-1 : Now , 'i' is on 13 and 'j' is on 14.
                                     Curr_API : {13 , 14} and d = 1
                                     Maine 'i' ko peeche kiya but 10 and 13 kaa diff is not equal to 13,14. i.e. 'i' wil further decreased by 1
                                     i.e. 'i' on 7 : {7,13} kaa diff is not matching with diff of {13,14}. i.e. further decreased 'i' by 1.
                                     in this pair also we got different 'd' and further 'i' ko decrease kiya toh elements khatam ho gaye.
                                     i.e. iss case main AP i.e. {13,14}


                                    Now , j+1

                            Step-2 : 'i' on 13 and 'j' on 19. Curr API : {13 , 19}  and d = 6
                                      Main fir 'i' ke peeche jaane laga and {10,13} kaa diff = 3 which is not equal to the diff of {13,19}.
                                      Main fir 'i' ke peeche jaane laga and {7,13} kaa diff = 6 which is equal to the diff of {13,19}.

                                      Hence 7 is added to the end of the array. i.e. AP now is : {7 , 13 , 19}
                                      And main further 'i' ko peeche le gaya. i.e. 'i' on 1
                                      {1,7} kaa diff = 6 which is equal to the diff of {13,19}.
                                      Hence 1 is added to the end of the array. i.e. AP now is : {1 , 7 , 13 , 19}

                                      ans = 4

                                      And j+1.

                            Similarly , we'll do the same but the longest AP that we can get from the given array is : {1 , 7 , 13 , 19}


           Note : if (n == 1) i.e. sirf ek hee element hain then AP length remains : 1
                  if (n == 2} then our AP length remains : 2

                  i.e. if (n <= 2) then ans = n



 */

public class LAS {

    public static void main(String[] args) {

       int []arr = {1 , 7 , 10 , 13 , 14 , 19};
       int n = arr.length;

       int ans = lengthOfLongestAP (arr , n);
       System.out.println(ans);
    }

    // Backward checkup i.e. decreasing 'i' is done in this function.
    //recursion
    // tc : O(2^n)
    // sc : O(n)
    public static int solve (int i , int diff , int []arr) {

        // Base Case
        // Jab mera 'i' < 0 ho jaaye tab rukna hain.
        if (i < 0) {
            return 0;
        }

        int ans = 0;

        // Let's say curr_i on 10 toh main 10 ke peeche bhaagta thaa i.e. decreasing 'i' beyond 10.
        for (int j = i-1 ; j >= 0 ; j--) {

            // 'i' ko peeche le jaate hue maine aise 2 pairs find out kar liye jinka common_diff match kar gaya.
            // i.e. hamn ek case solve kar liyaa. Toh recursion bolta hain ek case tum solve kardo baaki sub-problems main solve kar dunga.
            // Since , numbers are in sorted manner i.e. arr[i] is subtracted by arr[j]
            if (arr[i] - arr[j] == diff) {

                // Ek case solve kar liya i.e. 1 + subproblem
                // Let's say mere last ke 2 numbers 10 and 13 the aur jab main 'i' se peeche gaya toh mujhe mile 10 and 7.
                // Toh maine 7 ko include toh kar liya par iske peeche bhi toh answer ho sakta hain.
                ans = Math.max (ans , 1 + solve (j , diff , arr));
            }
        }

        return ans;
    }

    public static int lengthOfLongestAP (int []arr , int n) {

        if (n <= 2)
            return n;

        int ans = 0;
        for (int i = 0 ; i < n ; i++) {

            for (int j = i+1 ; j < n ; j++) {

                // Why we are adding : 2 ?
                // Let's say First two pairs the {13 , 19} and then inke baad ham 'i' ko peceh le jaa kar aur pairs
                // find kar rahe the. But {13 , 19} kii length bhi toh add hogyi. i.e. solve () function toh peeche jaakar
                // pairs find karega jo kii AP bana rahe hongye with {13 , 19} but curr_AP toh {13,19} hain i.e.
                // kuch bhi ho ans = 2 toh aayega hee.
                ans = Math.max (ans , 2 + solve (i , arr[j] - arr[i] , arr));
            }
        }

        return ans;
    }

}
