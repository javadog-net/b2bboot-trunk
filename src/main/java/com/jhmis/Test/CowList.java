package com.jhmis.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.Test
 * @Author: hdx
 * @CreateTime: 2020-01-21 15:25
 * @Description: CowList
 */
public class CowList {
    public static void main(String[] args) {
        Vector v = new Vector();
        Collections.synchronizedList(new ArrayList());
        CopyOnWriteArrayList cwa = new CopyOnWriteArrayList();
        ArrayList a = new ArrayList();
        List b = new ArrayList();
        new Demo().say();
    }
}
class Demo{
     void say(){

    }
}
