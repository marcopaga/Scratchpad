package de.mpaga.learn.spring.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@Cacheable("default")
public class CachedServiceImpl implements CachedService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CachedService.class);

    @Cacheable("default")
    public String longRunning(String input){
        LOGGER.info("longRunning operation really called");

        LOGGER.info("Finished waiting for long running operation.");
        return "success";
    }

    @CacheEvict(value = "default" , allEntries = true)
    public void evict(){
        LOGGER.info("evict");
    }

}
