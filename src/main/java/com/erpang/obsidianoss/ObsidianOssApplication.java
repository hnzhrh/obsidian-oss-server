package com.erpang.obsidianoss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ObsidianOssApplication {

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(ObsidianOssApplication.class);
        builder.headless(false).run(args);
//        SpringApplication.run(ObsidianOssApplication.class, args);
    }

}
