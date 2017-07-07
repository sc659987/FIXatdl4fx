package org.atdl4j.fixatdl.utils;

public class Utils {

    public static final boolean isEmptyString(String s) {
        return s != null && !s.isEmpty() ? false : true;
    }

    public static final boolean isNonEmptyString(String s) {
        return s != null && !s.isEmpty() ? true : false;
    }

}
