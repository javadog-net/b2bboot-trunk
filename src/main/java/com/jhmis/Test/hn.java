package com.jhmis.Test;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.Test
 * @Author: hdx
 * @CreateTime: 2020-01-14 21:58
 * @Description: hn
 */
public class hn {
    public static void main(String[] args) {
        int[] arrays = {1, 1, 2, 3, 5, 8, 13, 21};
        //bubbleSort(arrays, 0, arrays.length - 1);
        //int fibonacci = fibonacci(10);
        hanoi(4, 'A', 'B', 'C');
        System.out.println("公众号：Java3y" );
    }
    /**
     * 汉诺塔
     * @param n n个盘子
     * @param start 起始柱子
     * @param transfer 中转柱子
     * @param target 目标柱子
     */
    public static void hanoi(int n, char start, char transfer, char target) {
        //只有一个盘子，直接搬到目标柱子
        if (n == 1) {
            System.out.println(start + "---->" + target);
        } else {
            //起始柱子借助目标柱子将盘子都移动到中转柱子中(除了最大的盘子)
            hanoi(n - 1, start, target, transfer);
            System.out.println(start + "---->" + target);
            //中转柱子借助起始柱子将盘子都移动到目标柱子中
            hanoi(n - 1, transfer, start, target);
        }
    }
}
