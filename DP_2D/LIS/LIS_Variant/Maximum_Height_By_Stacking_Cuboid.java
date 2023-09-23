package DP_2D.LIS.LIS_Variant;

/*

Given n cuboids where the dimensions of the ith cuboid is cuboids[i] = [widthi, lengthi, heighti] (0-indexed). Choose a subset of cuboids and place them on each other.

You can place cuboid i on cuboid j if widthi <= widthj and lengthi <= lengthj and heighti <= heightj. You can rearrange any cuboid's dimensions by rotating it to put it on another cuboid.

Return the maximum height of the stacked cuboids.



Example 1:

img.png

Input: cuboids = [[50,45,20],[95,37,53],[45,23,12]]
Output: 190
Explanation:
Cuboid 1 is placed on the bottom with the 53x37 side facing down with height 95.
Cuboid 0 is placed next with the 45x20 side facing down with height 50.
Cuboid 2 is placed next with the 23x12 side facing down with height 45.
The total height is 95 + 50 + 45 = 190.

Example 2:

Input: cuboids = [[38,25,45],[76,35,3]]
Output: 76
Explanation:
You can't place any of the cuboids on the other.
We choose cuboid 1 and rotate it so that the 35x3 side is facing down and its height is 76.

Example 3:

Input: cuboids = [[7,11,17],[7,17,11],[11,7,17],[11,17,7],[17,7,11],[17,11,7]]
Output: 102
Explanation:
After rearranging the cuboids, you can see that all cuboids have the same dimension.
You can place the 11x7 side down on all cuboids so their heights are 17.
The maximum height of stacked cuboids is 6 * 17 = 102.


Constraints:

n == cuboids.length
1 <= n <= 100
1 <= widthi, lengthi, heighti <= 100


 */

/*

Kya ye jaruri hain kya main 50 ko width , 45 ko height and 20 ko length maanu ----> No 🙅

i = 0    : [50,45,20]   --------> So, sort this array :  =======> [20 , 45 , 50]
i = 1    : [95 , 37 , 33]  -------> [33 , 37 , 95]
i = 2    : [45 , 23 , 12]  -------> [12 , 23 , 45]

Since , mujhe height maximum banaani hain i.e. 50 , 95 and 45 ko height maan lunga whereas bache hue main se
kisi ko bhi width and length maan lo.

Approach :-

1. Sort all the dimension for every cuboid.

2. Sort elements inside the arrays i.e. sort all dimesions for every cuboid. To get the maximum height.

3. Abb man chahta huun sabse neeche sabse bada and then top par sabse choti dimension waala ho.
   i.e. sort the arrays inside the array i.e. sorting [[20 , 45 , 50] , [33 , 37 , 95] , [12 , 23 , 45]]

   [[12 , 23 , 45] ,
    [20 , 45 , 50] ,
    [33 , 37 , 95]]

    So, we had sorted the cuboids on the basis of their base i.e. (33 , 37) , (20 , 45) and (12 , 23)

    Since , we want maximum height


    Above logic is same as LIS : i.e. if curr_cuboid's dimension are same as prev_cuboid dimensions then include that else exclude that.

  4. Use Logic of LIS


 */

import java.util.ArrayList;
import java.util.Arrays;

public class Maximum_Height_By_Stacking_Cuboid {

    public static void main(String[] args) {

        int [][]cuboids = {{50,45,20} , {95 , 37 , 53} , {45 , 23 , 12}};
        int ans = Max_Height (cuboids);

        System.out.println(ans);
    }

    public static int Max_Height (int [][]cuboids ) {

        // Step-1 :  Sort all dimensions for every cuboid
        for (int arr[] : cuboids) {

            Arrays.sort (arr);
        }


        // Step-2 : Sort all cuboids on the basis of width and length
        Arrays.sort(cuboids, (a, b) -> Integer.compare(a[0],b[0])); // increasing order

        return solve (cuboids.length , cuboids);

    }

    // Step-3 : Use LIS Logic
    public static int solve (int n , int nums[][]) {

        int currRow[] = new int[n+1];
        int nextRow[] = new int[n+1];

        for (int curr = n-1 ; curr  >= 0 ; curr --) {

            for (int prev = curr-1;  prev >= -1 ; prev--) {

                // Included
                int take = 0;

                if (prev == -1 || check (nums[curr] , nums[prev])) {

                    // Since , i want the max height only i.e. adding the height instead of 1
                    take = nums[curr][2] + nextRow[curr  + 1];

                }

                // Excluded
                int notTake = nextRow[prev + 1];

                currRow[prev + 1] = Math.max (take , notTake);
            }

            nextRow = currRow; // Moving next upwards
        }

        return nextRow[0];
    }

    public static boolean check (int base[] , int newBox[]) {

        // width , length and height
        // i.e. newBox ko ham base ke upar rakh sakte hain.

        // newBox ko ham tabhi base cuboid par rakh sakte hain jab
        // newBox kii width , length and height base waale cuboid se kam ho.
        if (newBox[0] <= base[0] && newBox[1] <= base[1]  && newBox[2] <= base[2]) {

            return true;
        }

        return false;
    }
}
