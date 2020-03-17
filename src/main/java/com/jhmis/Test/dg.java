package com.jhmis.Test;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.Test
 * @Author: hdx
 * @CreateTime: 2020-01-15 13:38
 * @Description: dg
 */
public class dg {
    /**
     * userName : oven
     * userId : 1234567
     */

    private String userName;
    private String userId;

    public static void main(String[] args) {
        int[] arrays = {2, 3, 4, 5, 1, 66, 8, 99, 12};
        System.out.println(arrays[findMax(arrays,0,0)]);
    }
    public static int findMax(int[] arrays,int index,int max) {
        if(arrays.length==index+1){
            return arrays[max]>arrays[index]? max:index;
        }else{
            index++;
            max = findMax(arrays,index,max);
            if(arrays[max] > arrays[index]){
                return max;
            }else{
                return index;
            }
        }
    }

    public static int pop(int[] arrays,int index,int max) {
        if(arrays.length==index+1){
            return arrays[max]>arrays[index]? max:index;
        }else{
            index++;
            max = findMax(arrays,index,max);
            if(arrays[max] > arrays[index]){
                return max;
            }else{
                return index;
            }
        }
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
