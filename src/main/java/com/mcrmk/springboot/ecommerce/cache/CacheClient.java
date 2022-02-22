package com.mcrmk.springboot.ecommerce.cache;

public interface CacheClient<T> {

    T save(String classKey, String key, T data);

    T recover(String classKey, String key, Class<T> classValue);
}
