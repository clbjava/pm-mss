package com.pm.mss.comm.util;

public class Test {


    /**
     * 递归法
     * 思路：自上而下的方式。
     * 最后一步可能是从第n-1阶往上走1阶、从第n-2阶往上走2阶或从第n-3阶往上走3阶。
     * 因此，抵达最后一阶的走法，抵达这最后三阶的方式的综合。
     *
     * @param n
     * @return
     */
    public static int countWays(int n) {
        if (n < 0)
            return 0;
            //注意此处条件
        else if (n == 0)
            return 1;
        else {
            return countWays(n - 1) + countWays(n - 2) + countWays(n - 3);
        }
    }


    public static void main(String[] args) {
        int i = countWays(3);
        System.out.print(i);
    }
}
