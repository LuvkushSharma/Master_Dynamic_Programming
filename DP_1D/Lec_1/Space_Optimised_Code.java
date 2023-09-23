package DP_1D.Lec_1;

import java.util.Scanner;

public class Space_Optimised_Code {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();

        int ans = fibo (n);

        System.out.println(ans);

    }

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
