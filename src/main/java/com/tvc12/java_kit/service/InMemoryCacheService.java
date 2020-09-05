package com.tvc12.java_kit.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCacheService<K, V> implements CacheService<K, V> {
  final Map<K, V> cache = new ConcurrentHashMap<>();

  @Override
  public boolean put(K key, V value) {
    if (!cache.containsKey(key)) {
      synchronized (this) {
        if (!cache.containsKey(key)) {
          cache.put(key, value);
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public V get(K key) {
    return cache.get(key);
  }
}
