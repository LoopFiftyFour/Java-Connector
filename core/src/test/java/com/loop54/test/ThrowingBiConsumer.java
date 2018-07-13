package com.loop54.test;

import com.loop54.exceptions.Loop54Exception;
import com.loop54.exceptions.Loop54RuntimeException;

import java.util.function.BiConsumer;

/** {@link BiConsumer} that catches {@link Loop54Exception}s and wraps them as {@link Loop54RuntimeException}s. */
interface ThrowingBiConsumer<T, U> extends BiConsumer<T, U> {
    @Override
    default void accept(T t, U u) {
        try {
            throwingAccept(t, u);
        } catch (Loop54Exception e) {
            throw new Loop54RuntimeException(e);
        }
    }

    void throwingAccept(T t, U u) throws Loop54Exception;
}
