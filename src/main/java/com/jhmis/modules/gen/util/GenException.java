package com.jhmis.modules.gen.util;

public class GenException extends Exception {
	private static final long serialVersionUID = 4395633037242599294L;

	public GenException() {
    }

    public GenException(String s) {
        super(s);
    }

    public GenException(String s, Throwable throwable) {
        super(s);
        GenExceptionUtil.Fake(this, throwable);
    }
}
