package de.mpaga.learn.spring.cache;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.Resource;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class CachedServiceImplTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(CachedServiceImplTest.class);
    @Resource
    private CacheManager cacheManager;

    @Test
    public void test() throws Exception {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        CachedService cachedService = context.getBean(CachedService.class);

        CachedServiceImpl.resetNumberOfCalls();
        assertThat(CachedServiceImpl.getNumberOfCalls(), is(equalTo(0)));

        LOGGER.info("Expecting to wait...");
        cachedService.longRunning("test");
        assertThat(CachedServiceImpl.getNumberOfCalls(), is(equalTo(1)));

        LOGGER.info("Should not wait here...");
        cachedService.longRunning("test");
        cachedService.longRunning("test");
        assertThat(CachedServiceImpl.getNumberOfCalls(), is(equalTo(1)));

        LOGGER.info("Evicting...");
        cachedService.evict();
        assertThat(CachedServiceImpl.getNumberOfCalls(), is(equalTo(1)));

        LOGGER.info("Expecting to wait...");
        cachedService.longRunning("test");
        assertThat(CachedServiceImpl.getNumberOfCalls(), is(equalTo(2)));
    }
}
