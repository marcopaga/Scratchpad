package de.mpaga.learn.spring.cache;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.Resource;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CachedServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CachedServiceTest.class);
    @Resource
    private CacheManager cacheManager;

    @Test
    public void test() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        CachedService cachedService = context.getBean(CachedService.class);

        LOGGER.info("Expecting to wait...");
        cachedService.longRunning("test");

        LOGGER.info("Should not wait here...");
        cachedService.longRunning("test");
        cachedService.longRunning("test");

        LOGGER.info("Evicting...");
        cachedService.evict();

        LOGGER.info("Expecting to wait...");
        cachedService.longRunning("test");
    }
}
