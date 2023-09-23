package DP_2D.LIS;

/*

     I/P --->  {5  , 8  , 3  , 7  ,  9  ,  1}

     Prev soln :-

     [5 , 8]
     [3 , 7 , 9]
     [1]

     We are saving all the LIS and we only want the length , we donot want to print them i.e. moving to next approach.

     5 daal sakta huun ? ---> 👍
     [5]

     8 daal sakta huun ? ---> 👍
     [5 , 8]

     3 daal sakta huun ? ---> No

     Hamne 3 se just bada element pakad liya and use 3 se replace kar dia ---> 5 replaced with 3

     [3 , 8]

     7 daal sakta huun ? ---> No

     Hamne 7 se just bada element pakad liya and use 7 se replace kar dia ---> 8 replaced with 7

     [3 , 7]

     9 daal sakta huun ? ---> 👍
     [3 , 7 , 9]

     1 daal sakta huun ? ---> No

     Hamne 1 se just bada element pakad liya and use 1 se replace kar dia ---> 3 replaced with 1

     [1 , 7 , 9]

     1 , 7 , 9 ----> Not a Subsequence but it's length will be the ans.
 */


import java.util.ArrayList;

public class DP_With_Binary_Search_Optimised {

    public static void main(String[] args) {

        int nums[] = {5  , 8  , 3  , 7  ,  9  ,  1};
        int n = nums.length;

        int ans = solve_Optimal (n , nums);
        System.out.println(ans);
    }

    public static int solve_Optimal (int n , int nums[]) {

        if (n == 0) {

            return 0;
        }

        ArrayList<Integer> ans = new ArrayList<>();

        ans.add (nums[0]);

        for (int i = 1 ; i < n ; i++) {

            if (nums[i] > ans.get(ans.size()-1)) {

                ans.add (nums[i]);

            } else {

                // find index of jusst bada element in ans
                int idx = Search (nums , i , ans.size()-1 , nums[i]);
                ans.set(idx , nums[i]);

            }
        }

        return ans.size();
    }

    public static int Search (int  arr[] , int si , int ei , int item) {

        int ans = 0;

        while (si <= ei) {

            int mid = (si + ei)/2;

            if (arr[mid] >= item) {

                ans = mid;
                ei = mid - 1;

            } else {

                si = mid + 1;
            }
        }

        return ans;
    }
}
