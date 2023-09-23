package DP_2D.Minimum_Sideways_Jump;

/*

There is a 3 lane road of length n that consists of n + 1 points labeled from 0 to n. A frog starts at point 0 in the second lane and wants to jump to point n. However, there could be obstacles along the way.

You are given an array obstacles of length n + 1 where each obstacles[i] (ranging from 0 to 3) describes an obstacle on the lane obstacles[i] at point i. If obstacles[i] == 0, there are no obstacles at point i. There will be at most one obstacle in the 3 lanes at each point.

For example, if obstacles[2] == 1, then there is an obstacle on lane 1 at point 2.
The frog can only travel from point i to point i + 1 on the same lane if there is not an obstacle on the lane at point i + 1. To avoid obstacles, the frog can also perform a side jump to jump to another lane (even if they are not adjacent) at the same point if there is no obstacle on the new lane.

For example, the frog can jump from lane 3 at point 3 to lane 1 at point 3.
Return the minimum number of side jumps the frog needs to reach any lane at point n starting from lane 2 at point 0.

Note: There will be no obstacles on points 0 and n.



Example 1:

img

Input: obstacles = [0,1,2,3,0]
Output: 2
Explanation: The optimal solution is shown by the arrows above. There are 2 side jumps (red arrows).
Note that the frog can jump over obstacles only when making side jumps (as shown at point 2).
Example 2:

img_1

Input: obstacles = [0,1,1,3,3,0]
Output: 0
Explanation: There are no obstacles on lane 2. No side jumps are required.

Example 3:

img_2

Input: obstacles = [0,2,1,0,3,0]
Output: 2
Explanation: The optimal solution is shown by the arrows above. There are 2 side jumps.


Constraints:

obstacles.length == n + 1
1 <= n <= 5 * 105
0 <= obstacles[i] <= 3
obstacles[0] == obstacles[n] == 0

 */

/*


Given : frog is in the lane-2 and at posn = 0. Target is that frog ko hame end waali posn par laana hain i.e
        Hame frog ko posn 0 to posn 4 laana hain using minimum sideways jumps.


        i.e. Raasta itna asaan nhi hain. Beech main obstacles hain bhi hain. Sideways jumps means frog yaa fir toh Lane_1 par chala jaaye
        yaa fir Lan_3 par from Lane_2.

        ________________________________________

  Lane_1             🪨
        ________________________________________

  Lane_2   Frog               🪨
        _________________________________________

  Lane_3                              🪨
        ________________________________________
            0        1         2       3      4


            So, Posn = 0 par koi obstacle nhi hain isliye sideways jumps = 0 and frog straight move karega.
            But abb main posn = 1 to posn = 2 straight nhi jaa sakta beacuse Lane_2 par posn = 2 par Obstacle hain. i.e.
            main sideways jump karunga. Kya main Lane_1 par jaa sakta huun if i am currently on posn = 1 then No ❌ becoz
            in Lane_1 and posn = 1 par obstacle hain i.e. maain sideways jump on Lane_1 nhi kar sakta. So, main Lane_3 par sideways jump kiya
            and sideways_jumps '0' se '1' ho gaya.

                 ________________________________________

          Lane_1            🪨
                ________________________________________

          Lane_2                       🪨
                _________________________________________

          Lane_3            Frog               🪨
                ________________________________________
                    0        1         2       3      4

            current , sideways_jumps = 1
            Now , frog can move directly from posn = 1 to posn = 2 in Lane_3

                ________________________________________

          Lane_1            🪨
                ________________________________________

          Lane_2                       🪨
                _________________________________________

          Lane_3                     Frog     🪨
                ________________________________________
                    0        1         2       3      4

            current , sideways_jumps = 1
            Now , frog cannot move directly to posn = 3 as we have a obstacle in Lane_3. So , try to change the Lane by side jumping.
            Kya main Lane_2 par jump kar sakta huun ❌ (No).
            Kya main Lane_1 par jump kar sakta huun ✔️(Yes)


            current , sideways_jumps = 2

                ________________________________________

          Lane_1            🪨        Frog
                ________________________________________

          Lane_2                       🪨
                _________________________________________

          Lane_3                              🪨
                ________________________________________
                    0        1         2       3      4

                Now , frog can directly go to posn = 3 and from posn = 3 to posn = 4. Since , no obstacles are there in the Lane_1
                from posn = 2 to posn = 4.

                ________________________________________

          Lane_1            🪨                     Frog 🥇
                ________________________________________

          Lane_2                       🪨
                _________________________________________

          Lane_3                              🪨
                ________________________________________
                    0        1         2       3      4
                Hence , mujhe kitne sideways jumps lagye :

                ans = 2


       ---------------------------------------------------------------------------------------------

       obstacle = [0,1,2,3,0]

 index :       0          1              2            3         4
             { 0    ,     1       ,      2      ,     3       , 0}

             So, given array kaa matlab hain :

             0th posn par obstacle hamaara 0th Lane par pada hain.
             1st posn par obstacle hamaara 1st lane par pada hain.
             2nd posn par obstacle hamaara 2nd lane par pada hain.
             3rd posn par obstacle hamaara 3rd lane par pada hain.
             4th posn par obstacle hamaara 0th lane par pada hain.


            So, obstacle[i] bolta hain kii 'ith' pson par obstacle kis lane par pada hain.
            e.eg obstacle[1] = 3 ---> means 1st posn par obstacle 3rd lane par pada hain.

 */

/*

---------------------- Approach --------------------

                ________________________________________

          Lane_1            🪨
                ________________________________________

          Lane_2  Frog                 🪨
                _________________________________________

          Lane_3                              🪨
                ________________________________________
                    0        1         2       3      4


          So, check krlo pehle kahi frog target posn par pahauch toh nhi gaya i.e.

          Step-1 : if (posn == n) return 0;

          Step-2 : Check karlo kii agye waali posn par obstacle pada hain kya ? i.e. if frog at posn 0 then kya woh posn = 1 par jaa
                   sakta hain directly. If yes then ham seedhe chalengye i.e. faaltu main sideways jumps nhi lagaayengye.
                   i.e. agar agli posn par same lane main obstacle nhi hain then move straight.

                   if (obstacle[posn + 1] != currentLane) {

                         goes straight ------------>
                   }

                ________________________________________

          Lane_1            🪨
                ________________________________________

          Lane_2           Frog        🪨
                _________________________________________

          Lane_3                              🪨
                ________________________________________
                    0        1         2       3      4

         Step-3 : Abb toh sideways jump karna hee padega to move from posn = 1 to posn = 2. Toh maine Lane_1 par try kiya
                  jump karne kaa but wahan par obstacle hain. Mere pass 3 choices the i.e. yaa fir issi row main reh jaau ,
                  Lane_1 par jump karlu yaa fir Lane_3 par jump karlu.


                  Choice_2
                  |
                  |
                  |
                  🐸  ---------- Choice_1 (Not a sideways jump)
                  |
                  |
                  |
                  Choice_3

                  Hence , Choice_1 and Choice_3 are sideways jumps according to the Frog's current posn or current_lane.


                  so, main loop laga dunga.

                  for (i = 1 to i = 3)

                  but i = 2 toh possible hee nhi hain. Toh maine bol diya jisbhi current lane par main khada huun woh != i hona chaiye.


                  if (current_lane != i) <--- matlab jispar main jump karna chahta  huun woh meri current lane nhi honi chaiye.

                  i.e. jahan par thaa wahi par jump kar raha huun iska koi matlab hee nhi banta. i.e. main posn = 1 , Lane_2 par khada huun
                  and Lane_2 , posn = 1 par hee jump maar raha huun. Aisa mat karna.

                  Aur agar sideways par jump karna hain toh woh tabhi possible hain jab wahan par Obstacle naa ho.

                  i.e. if (........ && obstacle[posn] != i) <---- obstacle[posn] matlab jahan par obstacle pada hua hain. So, woh not equal
                  to hona chaiye jahan par main jump karna chahta huun.

                  Hence ,

                  if (current_lane != i && obstacle[posn] != i) {

                     sidewaysJump += 1;
                  }

                  -----------------------------------

                  if (posn == n)
                     return 0;

                  if (obs[posn+1] != currentLane) { <---- direct move

                     --------->

                  } else {

                     sideways jump i.e.
                     if (current_lane != i && obstacle[posn] != i) {

                         sidewaysJump += 1;
                     }

                  }



               Special Case :-

               Iss case main yaa fir toh main upar waali lane main jaa skata thaa yaa fir neeche waali main i.e.2 ways soln but hame
               minimum ans chaiye.

               ________________________________________

          Lane_1            🪨
                ________________________________________

          Lane_2            🪨        Frog     🪨
                _________________________________________

          Lane_3
                ________________________________________
                    0        1         2       3      4




                        (1,n)
                      /
                     /                                      (1,n) , (2,n) and (3,n) are the destinations.
                    /
           dp[i][j] --------------> (2,n)
                   \
                    \
                      \
                       (3,n)


         dp[i][j] bolta hain (i,j) se inn teeno posn i.e. destination par pahauchne ke mere kitne minimum number of sideways lagye.

 */

import java.util.Arrays;

public class minSidewaysJumps {

    public static void main(String[] args) {

        int []obstacle = {0,1,2,3,0};

        // ----------- Recursion -----------
        int ans1 = solve_recur (obstacle , 2 , 0 , obstacle.length - 1); // frog in the starting will be at Lane_2 and posn = 0
        System.out.println(ans1);

        // ----------- Memoization ----------

        // Step-1 :
        int [][]dp = new int[4][obstacle.length]; // 4 Lanes and posn is obstacle.length

        for (int arr[] : dp) {

            Arrays.fill (arr , -1);
        }

        int ans2 = solve_memo (obstacle , 2 , 0 , obstacle.length - 1 , dp);
        System.out.println(ans2);

        // -------------------- Tabulation ----------------
        int ans3 = solve_tabu (obstacle , obstacle.length - 1);
        System.out.println(ans3);

        // ------------------- Space Optimization -------------
        int ans4 = solve_so (obstacle , obstacle.length-1);
        System.out.println(ans4);

    }

    // ##################### Recursion #################

    public static int solve_recur (int []obstacle , int currlane , int currposn , int n) {

        // Base Case
        if (currposn == n) {
            return 0;
        }

        // Check karo same lane par agli posn par obstacle toh nhi
        // Agar nhi hain then move straight and agla part solve karne ke liye recursion se bol diya.
        if (obstacle[currposn + 1] != currlane) {

            return solve_recur (obstacle , currlane , currposn + 1 , n); // same lane hogyi but posn agye bhad jaayegyi

        } else {

            // Sideways jumps
            // Mere pass ginti kii 3 hee choice hain.
            int ans = Integer.MAX_VALUE;
            for (int i = 1 ; i <= 3 ; i++) {

                // Jiss bhi lane par jump kar rahe ho woh same lane nhi honi chaiye and
                // Jiss bhi lane par jump kar rahe ho uspar obstacle nhi hona chaiye.
                if (currlane != i && obstacle[currposn] != i) {

                    // Since , 1 sideways jump occur i.e. 1 + solve ()
                    ans = Math.min (ans , 1 + solve_recur (obstacle , i , currposn , n));
                }
            }

            return ans;
        }

    }

    // ############################# Memoization #########################

    /*

    Dono function call main 2 variables kii states change ho rahi hain i.e. currlane and currposn i.e. we had applied 2D Dp

     */

    public static int solve_memo (int []obstacle , int currlane , int currposn , int n , int [][]dp) {

        // Base Case
        if (currposn == n) {
            return 0;
        }

        // Step-3 :
        if (dp[currlane][currposn] != -1) {

            return 0;
        }

        if (obstacle[currposn + 1] != currlane) {

            return solve_memo (obstacle , currlane , currposn + 1 , n , dp);

        } else {

            int ans = Integer.MAX_VALUE;
            for (int i = 1 ; i <= 3 ; i++) {

                if (currlane != i && obstacle[currposn] != i) {

                    ans = Math.min (ans , 1 + solve_memo (obstacle , i , currposn , n , dp));
                }
            }

            // Step-2:
            dp[currlane][currposn] = ans;

            return ans;
        }

    }

    // ######################### Tabulation ####################

    /*
             In Top-Down : currlane starts from 2 and goes till 0
                           currposn starts from 0 and goes till obstacle.length


  i.e.
                        (1,n)
                      /
                     /                                      (1,n) , (2,n) and (3,n) are the destinations.
                    /
           (2, 0) --------------> (2,n)
                   \
                    \
                      \
                       (3,n)


             In bottom-up : Just reverse of it i.e.

             (1,n)    ------------------------------------------------>

             (2,n)    ------------------------------------------------>  (2,0)

             (3,n)    ------------------------------------------------>


             i.e. Main Destination to (2,0) reach karne kaa try kar raha huun.

                      Way-1
             (1,0)    ------------------------------------------------  (1,n)

                      Way-2
             (2,0)    ------------------------------------------------  (2,n)

                      Way-3
             (3,0)    ------------------------------------------------  (3,n)


             So, directly gaya (2,n) to (2,0) then Way-2 hua but main Way-3 se bhi toh aa sakta huun ek jump maarke i.e. 1 + dp[3][0]
             Similarly main jab (1,n) se (1,0) par gaya toh ek jump maarke (2,0) par aa sakta huun i.e. 1 + dp[1][0]


     */

    public static int solve_tabu (int []obstacle , int n) {

        int INF = (int) 1e9;

        // Step-1 :
        int [][]dp = new int[4][obstacle.length]; // 4 Lanes and posn is obstacle.length

        for (int []arr : dp) {

            Arrays.fill (arr , INF);
        }

        // Step-2 :
        dp[0][n] = 0;
        dp[1][n] = 0;
        dp[2][n] = 0;
        dp[3][n] = 0;

        // Main (1,n) , (2,n) , (3,n) kaa answer already fill kar chuka huun toh inhe dubaara process karne kii need nhi hain.
        // i.e. we'll start from (1,n-1) , (2,n-1) and (3,n-1)

        for (int currposn = (n-1) ; currposn >= 0 ; currposn--) {

            for (int currlane = 1 ; currlane <= 3 ; currlane++) {

                if (obstacle[currposn + 1] != currlane) {

                    dp[currlane][currposn] = dp[currlane][currposn + 1];

                } else {

                    int ans = INF;
                    for (int i = 1 ; i <= 3 ; i++) {

                        if (currlane != i && obstacle[currposn] != i) {

                            // ans = Math.min (ans , 1 + dp[i][currposn]); <----------- Giving WRONG ANSWER
                            ans = Math.min (ans , 1 + dp[i][currposn + 1]);
                        }
                    }

                    // Step-2:
                    dp[currlane][currposn] = ans;

                }
            }
        }


        // Step-3 :
        return Math.min (dp[2][0] , Math.min(1 + dp[1][0] , 1 + dp[3][0]));

    }

    // -------------------------------- Space Optimization ----------------------------------

    /*

    dp[currlane][currposn] ----> depends on dp[currlane][currposn + 1]    and    dp[i][currposn + 1]

    dp[currlane][currposn + 1] means hamaari lane same hain but posn change ho gayi hain.

    dp[i][currposn + 1]  means different lane and next posn.i.e. mujhe sirf 2 hee columns kii need hain.

     */

    public static int solve_so (int []obstacle , int n) {

        int INF = (int) 1e9;

        int []currColm = new int[4];
        Arrays.fill ( currColm , Integer.MAX_VALUE);

        int []nextColm = new int[4];
        Arrays.fill ( nextColm , Integer.MAX_VALUE);

        nextColm[0] = 0;
        nextColm[1] = 0;
        nextColm[2] = 0;
        nextColm[3] = 0;



        for (int currposn = (n-1) ; currposn >= 0 ; currposn--) {

            for (int currlane = 1 ; currlane <= 3 ; currlane++) {

                if (obstacle[currposn + 1] != currlane) {

                    currColm[currlane] = nextColm[currlane];

                } else {

                    int ans = INF;
                    for (int i = 1 ; i <= 3 ; i++) {

                        if (currlane != i && obstacle[currposn] != i) {

                            // ans = Math.min (ans , 1 + dp[i][currposn]); <----------- Giving WRONG ANSWER
                            ans = Math.min (ans , 1 + nextColm[i]);
                        }
                    }

                    // Step-2:
                    currColm[currlane] = ans;

                }
            }

            nextColm = currColm;
        }


        // Step-3 :
        return Math.min (nextColm[2] , Math.min(1 + nextColm[1] , 1 + nextColm[3]));

    }


}
