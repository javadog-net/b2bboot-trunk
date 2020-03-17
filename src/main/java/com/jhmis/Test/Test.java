package com.jhmis.Test;

import com.alibaba.fastjson.JSON;

import java.util.*;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.Test
 * @Author: hdx
 * @CreateTime: 2020-01-11 20:44
 * @Description: 测试
 */
public class Test {
    /*public static void main(String[] args) {
        List<Person> a = new ArrayList<Person>();

        a.add(new Person("huhu",18));
        a.add(new Person("yueyue",17));
        a.add(new Person("chuanchuan",1));
        Collections.sort(a, new Comparator<Person>() {
            @Override
            public int compare(Person o1, Person o2) {
                return  o2.getAge() - o1.getAge();
            }
        });
        //System.out.println(a.hashCode());
        System.out.println("1".hashCode());
        System.out.println("2".hashCode());
        System.out.println(JSON.toJSONString(new Person("chuanchuan",1)));
        String o = JSON.toJSONString(new Person("chuanchuan",1));
        JSON.parseObject(o,Person.class);
        Set s = new HashSet();
    }*/
    public static void main(String[] args) {
        CategoryDao cd = new CategoryDao();
        Person p = new Person("huhu",18);
        cd.add(p);
        BubbleSort();
    }

    /**
     *
     * @param
     * @return
     */
    public static void BubbleSort() {
        int [] arr = new int[]{11,45,3,7,2,9,33,1};
        for(int i=0; i<arr.length -1; i++){
            for(int j=0; j<arr.length-i -1;j++){
                int temp = arr[i];
                if(arr[j]>arr[j+1]){
                    temp = arr[j+1];
                    arr[j+1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        for(int k=0; k<arr.length; k++){
            System.out.println(arr[k]);
        }
    }



}

class Java3y {


    private Java3y() {
    }

    private static Java3y java3y = null;


    public static Java3y getJava3y() {
        if (java3y == null) {
            // 将锁的范围缩小，提高性能
            synchronized (Java3y.class) {
                java3y = new Java3y();
            }
        }
        return java3y;
    }
}

class TestDemo {

    public static void main(String[] args) {

        // 线程A
        new Thread(() -> {

            // 创建单例对象
            Java3y java3y = Java3y.getJava3y();
            System.out.println(java3y);

        }).start();

        // 线程B
        new Thread(() -> {
            // 创建单例对象
            Java3y java3y = Java3y.getJava3y();
            System.out.println(java3y);
        }).start();

        // 线程C
        new Thread(() -> {
            // 创建单例对象
            Java3y java3y = Java3y.getJava3y();
            System.out.println(java3y);
        }).start();

    }
}
