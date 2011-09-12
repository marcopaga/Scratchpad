package de.mpaga.learn.spring.cache;

public interface CachedService {

    public String longRunning(String input);

    public void evict();

}
