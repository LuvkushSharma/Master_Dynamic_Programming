package DP_with_Distinct_Ways.Minimum_Swaps_To_Make_Sequence_Increasing;

/*

You are given two integer arrays of the same length nums1 and nums2. In one operation, you are allowed to swap nums1[i] with nums2[i].

For example, if nums1 = [1,2,3,8], and nums2 = [5,6,7,4], you can swap the element at i = 3 to obtain nums1 = [1,2,3,4] and nums2 = [5,6,7,8].
Return the minimum number of needed operations to make nums1 and nums2 strictly increasing. The test cases are generated so that the given input always makes it possible.

An array arr is strictly increasing if and only if arr[0] < arr[1] < arr[2] < ... < arr[arr.length - 1].



Example 1:

Input: nums1 = [1,3,5,4], nums2 = [1,2,3,7]
Output: 1
Explanation:
Swap nums1[3] and nums2[3]. Then the sequences are:
nums1 = [1, 3, 5, 7] and nums2 = [1, 2, 3, 4]
which are both strictly increasing.

Example 2:

Input: nums1 = [0,3,5,8,9], nums2 = [2,1,4,6,9]
Output: 1


Constraints:

2 <= nums1.length <= 105
nums2.length == nums1.length
0 <= nums1[i], nums2[i] <= 2 * 105

 */

/*

Strictly increasing means arr[0] < arr[1] < arr[2] < arr[3] <..... < arr[n]
And no duplicate elements should be there.


In the Dice problem we have two choices INC and EXC similarly , here we have two choices whether we can SWAP or Don't SWAP.

nums1[] = { 1  , 3  , 5  , 4}
            i

nums2[] = {1 , 2 , 3 , 7}

Mujhe ye toh check karna padega kii 1 se pehle koi bada number toh nhi. But iske peeche toh kuch hain hee nhi i.e. add kardo.

Constraint : numbers are +ve in the array so, we'll add a negative number.
i.e. nums1 = {-1 , 1 , 3 , 5 , 4}
                   |
                   V
                  idx
     nums2 = {-1 , 1 , 2 , 3 , 7}

 Step-1 : Add '-1' in the beginning of both the arrays.

 Step-2 : nums1 = {-1 , 1 , 3 , 5 , 4}
                        |
                        V
                       idx
          nums2 = {-1 , 1 , 2 , 3 , 7}
                        |
                        V
                        idx2

          Let's say main inn 2 numbers par khada huun toh mere pass 2 choices hain whether i will swap both or don't swap.

          Abb dhyaan se dekho , element at idx and idx2 are already greater than the previous element i.e. -1. Iska matlab hame swap karne
          kii need hee nhi hain.

          So, swap ----> par condn lagyi hain kii agar curr_ele > prev_ele then no need for swap and in that case we do noswap.

                       idx
                        |
                        V
          nums1 = {-1 , 1 , 3 , 5 , 4}
          nums2 = {-1 , 1 , 2 , 3 , 7}
                      /   \
                     /     \
              SWAP  /       \ NO SWAP
                   /         \         idx
                  /           \         |
                 ❌          { -1 , 1 , 3 , 5 , 4 }
                             { -1 , 1 , 2 , 3 , 7}    ---> Now idx on 3 and 2
                                      /   \
                                     /     \
                           SWAP     /       \ NO_SWAP      idx
                                   /         \             |
                                  ❌         { -1 , 1 , 3 , 5 , 4 }
                         Since , 3 > 1       { -1 , 1 , 2 , 3 , 7}   ----> Now idx on 5 and 3
                         and 2 > 1                         /  \
                                                          /    \
                                                SWAP     /      \ NOT SWAP         idx
                                                        /        \                  |
                                                       ❌        { -1 , 1 , 3 , 5 , 4 }
                                           Since, 5 > 3          { -1 , 1 , 2 , 3 , 7}   ----> Now idx on 4 and 7
                                           and 3 > 2                               /  \
                                                                     SWAP         /     \ NOT_SWAP
                                                                                 /       \
                                                                               ✔️
                                                                     Since, 4 < 5
                                                                                        idx
                                                                                         |
                                                                  { -1 , 1 , 3 , 5 , 7 }
                                                                  { -1 , 1 , 2 , 3 , 4}

                                                                              |
                                                                              |
                                                                              V
                                                                          Base Case
                                                                          ans = 1 (we had done 1 swap)

                                        idx
                                         |
                                         v
               nums1 = {-1 ,  1 ,  2  ,  5  , 4}
               nums2 = {-1 ,  1 ,  3  ,  3  , 7}

               isme 'idx' par dikkat ayegyi. Because 5 > 2 --> Yes But , 3 is not greater than 3.

               So,  Kaayede se inne swap karna chaiye aise :
                    nums1 = {-1 ,  1 ,  2  ,  3  , 4}
                    nums2 = {-1 ,  1 ,  3  ,  5  , 7}


               But , ham real array main swap nhi kar rahe hain isliye : swapped naam kaa boolean liya hain.

               So, reality main agar idx hamaara 4 and 7 par hogaa then prev1 must be 3 and prev2 must be 5. and swapped = true
               i.e. jab ham 4 and 7 par aaye then swapped = true hain.

               Since , hamne swapping nhi kari indexes par so, for 4 --> prev1 = 5 and for 7 --> prev2 = 3 ------> ❌ ⚠️⚠️

               So, ham indexes par swap nhi karengye. Instead of this ham integers ko swap karengye i.e. prev1 and prev2.

               if (swapped == true) {

                   swap(prev1 , prev2);
               }

 */


import java.util.ArrayList;
import java.util.Arrays;

public class Min_Swaps_to_make_seq_inc {

    public static void main(String[] args) {

        int []arr1 = {1 , 3 , 5 , 4};
        int []arr2 = {1 , 2 , 3 , 7};

        ArrayList<Integer> nums1 = new ArrayList<>();
        ArrayList<Integer> nums2 = new ArrayList<>();


        for (int i = 0 ; i < arr1.length ; i++) {

            nums1.add (arr1[i]);
            nums2.add (arr2[i]);
        }

        // It means that the previous indexes were swapped or not
        boolean swapped = false;

        // Step-1 : insert '-1' at the starting of the ArrayLists
        nums1.add (0 , -1);
        nums2.add (0 , -1);

        // --------------- Recursion -------------
        int ans1 = solve_recur (nums1 , nums2 , 1 , swapped);
        System.out.println(ans1);


        // --------------- Memoization -------------
        int n = nums1.size();
        int [][]dp = new int[n][2];

        for (int arr[] : dp) {

            Arrays.fill (arr , -1);
        }

        int ans2 = solve_memo (nums1 , nums2 , 1 , swapped , dp);
        System.out.println(ans2);

        // --------------- Tabulation -------------
        int ans3 = solve_tabu (nums1 , nums2);
        System.out.println(ans3);

        // --------------- Space Optimization -------------
        int ans4 = solve_so (nums1 , nums2);
        System.out.println(ans4);

    }

    // ################ Recursion ##############
    public static int solve_recur (ArrayList<Integer> nums1 , ArrayList<Integer> nums2 , int idx , boolean swapped) {

        // Base Case
        if (idx == nums1.size()) {

            return 0;
        }

        int ans = Integer.MAX_VALUE;

        int prev1 = nums1.get(idx - 1);
        int prev2 = nums2.get(idx - 1);

        if (swapped) {

             int temp = prev1;
             prev1 = prev2;
             prev2 = temp;
        }

        // No Swap
        if (nums1.get(idx) > prev1 && nums2.get(idx) > prev2) {

            // NO SWAP i.e. swapped = false
            ans = solve_recur (nums1 , nums2 , idx + 1 , false);

        }

        // SWAP
        /*
                       idx
                         |
            {......, 2 , 5 , 4}
            {......, 3 , 3 , 7}

            Above if says : 5 > 2 && 3 > 3 ----> false

            So, second condn : Since , ham asliyat main swap karengye. But hamne 5 ko compare kiya 3 se and 3 ko 2 se kii abb strictly
            increasing ban raha hain kii nhi.

         */
        if (nums1.get(idx) > prev2 && nums2.get(idx) > prev1) {

            // Since , we had swapped two elements i.e. --->  1 + solve ()
            ans = Math.min (ans , 1 + solve_recur (nums1 , nums2 , idx + 1 , true));

        }

        return ans;
    }



    // ################ Memoization ##############
    /*

        Two parameters are changing in the recursive call i.e. 2D DP
        parameters that are changing are : idx and swapped

     */
    public static int solve_memo (ArrayList<Integer> nums1 , ArrayList<Integer> nums2 , int idx , boolean swapped , int [][]dp) {

        // Base Case
        if (idx == nums1.size()) {

            return 0;
        }

        // Step-3 :
        int swap1 = swapped ? 1 : 0;
        if (dp[idx][swap1] != -1) {

            return dp[idx][swap1];
        }

        int ans = Integer.MAX_VALUE;

        int prev1 = nums1.get(idx - 1);
        int prev2 = nums2.get(idx - 1);

        if (swapped) {

            int temp = prev1;
            prev1 = prev2;
            prev2 = temp;
        }

        // No Swap
        if (nums1.get(idx) > prev1 && nums2.get(idx) > prev2) {

            // NO SWAP i.e. swapped = false
            ans = solve_memo (nums1 , nums2 , idx + 1 , false , dp);

        }

        // SWAP
        if (nums1.get(idx) > prev2 && nums2.get(idx) > prev1) {

            ans = Math.min (ans , 1 + solve_memo (nums1 , nums2 , idx + 1 , true , dp));

        }

        // Step-2 :
        int swap2 = swapped ? 1 : 0;

        dp[idx][swap2] = ans;

        return dp[idx][swap2];
    }



    // ################ Tabulation ##############
    /*

          In top-down : idx goes from 1 to N
                        swapped sended was false and returned can be true <---- given in question as 1 swap will be there for sure.

          In bottom-up : idx goes from N to 1
          and also reverse the swapped.
     */

    // TC : O(n)
    // SC : O(n) <-- beacuse colm is of 2 i.e. constant
    public static int solve_tabu (ArrayList<Integer> nums1 , ArrayList<Integer> nums2) {

        // Step-1 :
        int n = nums1.size();
        int [][]dp = new int[n + 1][2];

        // Step-2: Analyze the base case
        // if (idx == n) then fill dp with 0
        // already filled with 0.

        // for idx = n --> dp is already filled.
        for (int idx = n-1 ; idx >= 1 ; idx--) {

            for (int swap = 1 ; swap >= 0 ; swap--) {

                boolean swapped = swap == 1;

                int ans = Integer.MAX_VALUE;

                int prev1 = nums1.get(idx - 1);
                int prev2 = nums2.get(idx - 1);

                if (swapped) {

                    int temp = prev1;
                    prev1 = prev2;
                    prev2 = temp;
                }

                // No Swap
                if (nums1.get(idx) > prev1 && nums2.get(idx) > prev2) {

                    // NO SWAP i.e. swapped = false
                    ans = dp[idx + 1][0];

                }

                // SWAP
                if (nums1.get(idx) > prev2 && nums2.get(idx) > prev1) {

                    ans = Math.min (ans , 1 + dp[idx + 1][1]);

                }

                // Step-2 :
                int swap1 = swapped ? 1 : 0;

                dp[idx][swap1] = ans;

            }
        }

        return dp[1][0];
    }


    // ##########################  Space Optimization ####################
    /*

      dp[idx][swap] depends on ans
      ans depends on dp[idx+1][1]  and dp[idx+1][0]


      dp[idx][swap] --------------> dp[idx+1][0]
                    \
                      \
                        \
                          dp[idx+1][1]


        dp[idx][swap] depends on agli row ke 0th colm and 1st colm par.

        Since , idx goes from n-1 to 0 i.e. we are moving upwards.



     */

    public static int solve_so (ArrayList<Integer> nums1 , ArrayList<Integer> nums2) {

        int n = nums1.size();

        int swapRow = 0;
        int noSwapRow = 0;

        int currSwap = 0;
        int currNoSwap = 0;

        for (int idx = n-1 ; idx >= 1 ; idx--) {

            for (int swap = 1 ; swap >= 0 ; swap--) {

                boolean swapped = swap == 1;

                int ans = Integer.MAX_VALUE;

                int prev1 = nums1.get(idx - 1);
                int prev2 = nums2.get(idx - 1);

                if (swapped) {

                    int temp = prev1;
                    prev1 = prev2;
                    prev2 = temp;
                }

                // No Swap
                if (nums1.get(idx) > prev1 && nums2.get(idx) > prev2) {

                    // NO SWAP i.e. swapped = false
                    ans = noSwapRow;

                }

                // SWAP
                if (nums1.get(idx) > prev2 && nums2.get(idx) > prev1) {

                    ans = Math.min (ans , 1 + swapRow);

                }


                if (swapped) {

                    currSwap = ans;

                } else {

                    currNoSwap = ans;
                }

            }

            swapRow = currSwap;
            noSwapRow = currNoSwap;
        }

        return Math.min (swapRow , noSwapRow);
    }

}
