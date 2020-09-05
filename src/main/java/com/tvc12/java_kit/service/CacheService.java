package com.tvc12.java_kit.service;

public interface CacheService<K, V> {
  boolean put(K key, V value);

  V get(K key);
}
