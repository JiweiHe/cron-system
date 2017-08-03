package com.github.bone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Created by mike on 2017/8/2.
 */

@SpringBootApplication
public class CronApplication {
    private static ApplicationContext ctx;
    public static void main(String[] args) {
        ctx = SpringApplication.run(CronApplication.class, args);
    }
}
