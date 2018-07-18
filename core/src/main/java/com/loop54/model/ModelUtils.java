package com.loop54.model;

public class ModelUtils {
    public static <T> T numberSafeCast(Object o, Class<T> clazz) {
        if (clazz == Byte.class)
            return clazz.cast(((Number)o).byteValue());

        if (clazz == Double.class)
            return clazz.cast(((Number)o).doubleValue());

        if (clazz == Float.class)
            return clazz.cast(((Number)o).floatValue());

        if (clazz == Integer.class)
            return clazz.cast(((Number)o).intValue());

        if (clazz == Long.class)
            return clazz.cast(((Number)o).longValue());

        if (clazz == Short.class)
            return clazz.cast(((Number)o).shortValue());

        return (T)o;
    }
}
