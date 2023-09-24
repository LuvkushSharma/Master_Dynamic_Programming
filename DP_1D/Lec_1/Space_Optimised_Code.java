package DP_1D.Lec_1;

import java.util.Scanner;

public class Space_Optimised_Code {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        int ans = fibo (n);

        System.out.println(ans);

    }

    /*
    
        To know whether the space optimisation is applied or not !
        
        dp[i] is dependent on dp[i-1] and dp[i-2]  i.e.
        dp[i] is dependent on prev1 and prev2 elements.
        
        i.e. replace dp[i-1] with prev1 
        and replace dp[i-2] with prev2
    
     */

    public static int fibo (int n) {

        // Step-1 :
        int prev1 = 1;
        int prev2 = 0;

        if (n == 0)
            return prev2;

        for (int i = 2 ; i <= n ; i++) {

            int curr = prev1 + prev2;

            System.out.println("curr : " + curr + " , prev1 : " + prev1 + " , prev2 : " + prev2);

            // shift logic
            prev2 = prev1;
            prev1 = curr;
        }

        return prev1;
    }
}
