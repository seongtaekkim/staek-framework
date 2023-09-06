package com.staekframework.jdbc.util;

import com.staekframework.jdbc.exception.WrongSizeException;

import java.util.Collection;

public class DataAccessUtil {

    public static <T> T SelectOne(Collection<T> c) {
        if (c.isEmpty())
            return null;
        if (c.size() > 1)
            throw new WrongSizeException(1, c.size());
        return c.iterator().next();
    }
}
