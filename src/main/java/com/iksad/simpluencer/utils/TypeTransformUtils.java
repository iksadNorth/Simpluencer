package com.iksad.simpluencer.utils;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Function;

public class TypeTransformUtils {
    public static <T,U> Collection<U> map(Collection<T> before, Function<T,U> function) {
        HashSet<U> after = new HashSet<>();
        for(T role: before) {
            after.add(function.apply(role));
        }
        return after;
    }
}
