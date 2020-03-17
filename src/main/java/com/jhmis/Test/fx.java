package com.jhmis.Test;


import org.apache.poi.ss.formula.functions.T;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.Test
 * @Author: hdx
 * @CreateTime: 2020-01-16 14:53
 * @Description: fx
 */
public class fx {
    public static void main(String[] args) {
        List<Integer> no = Collections.synchronizedList(new ArrayList<Integer>());
        no.add(1);
        no.add(2);
        no.add(3);
        test(no);
        //show("huhu");
    }

    //定义泛型方法..
    public static  <T> void show(T t) {
        System.out.println(t);

    }

    public static void test(List<? extends Number> list){

        for(int i=0;i<list.size();i++){

            System.out.println(list.get(i));

        }
    }
}

