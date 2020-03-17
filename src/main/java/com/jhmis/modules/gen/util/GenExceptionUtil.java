//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.jhmis.modules.gen.util;

public class GenExceptionUtil {
    private GenExceptionUtil() {
    }

    public static Throwable Fake(Throwable throwable, Throwable throwable1) {
        try {
            throwable.getClass().getMethod("initCause", Throwable.class).invoke(throwable, throwable1);
        } catch (Exception var3) {
            ;
        }

        return throwable;
    }
}
