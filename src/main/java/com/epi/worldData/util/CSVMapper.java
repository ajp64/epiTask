package com.epi.worldData.util;

public interface CSVMapper<T, K> {

    T mapTo(K k);

    K unmapFrom(T t);
}
