package de.mpaga.learn.spring.cache;

import com.google.common.annotations.VisibleForTesting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@Cacheable("default")
public class CachedServiceImpl implements CachedService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CachedService.class);
    private static volatile int numberOfCalls = 0;

    @Cacheable("default")
    public String longRunning(String input){
        LOGGER.info("longRunning operation really called");

        numberOfCalls ++;
        return "success";
    }

    @CacheEvict(value = "default" , allEntries = true)
    public void evict(){
        LOGGER.info("evict");
    }

    protected static void resetNumberOfCalls(){
        numberOfCalls = 0;
    }

    @VisibleForTesting
    protected static int getNumberOfCalls() {
        return numberOfCalls;
    }
}
