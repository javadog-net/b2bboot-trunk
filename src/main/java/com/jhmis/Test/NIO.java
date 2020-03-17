package com.jhmis.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 一个低端小气没档次的程序狗 JavaDog
 * blog.javadog.net
 *
 * @BelongsProject: b2bboot
 * @BelongsPackage: com.jhmis.Test
 * @Author: hdx
 * @CreateTime: 2020-01-14 13:47
 * @Description: NIO
 */
public class NIO {
    public static void main(String[] args){
        try {
            copyFileWithNIO("./1.txt","2.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void copyFileWithNIO(String src, String dst) throws IOException {
        FileInputStream fin = new FileInputStream(new File(src));
        FileOutputStream fout = new FileOutputStream(new File(dst));

        FileChannel finChannel = fin.getChannel();
        FileChannel foutChannel = fout.getChannel();

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //用下面的效率更高
        //ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);

        while (true) {
            int eof = finChannel.read(buffer);
            if (eof == -1) {
                break;
            }
//            重新设置一下buffer的position=0， limit=position
            buffer.flip();
//            开始写
            foutChannel.write(buffer);
//            写完要重置buffer,重新设置position=0, limit=capacity
            buffer.clear();
        }
        finChannel.close();
        foutChannel.close();
        fin.close();
        fout.close();
    }
}
