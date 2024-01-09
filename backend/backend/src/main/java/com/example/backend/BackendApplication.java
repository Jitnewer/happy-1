package com.example.backend;

import io.imagekit.sdk.ImageKit;
import io.imagekit.sdk.config.Configuration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {
    public static void main(String[] args) {
//        ImageKit imageKit = ImageKit.getInstance();
//        Configuration config = new Configuration("public_3XDfZrNhQkEVo3hn7sdNumTGxBE=", "private_Xjw6BAvIHh1ccXrBy5JoCHfx6sM=", "https://ik.imagekit.io/happy1hva");
//        imageKit.setConfig(config);
        SpringApplication.run(BackendApplication.class, args);
    }
}
