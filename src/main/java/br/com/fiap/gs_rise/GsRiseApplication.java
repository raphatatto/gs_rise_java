package br.com.fiap.gs_rise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class GsRiseApplication {

    public static void main(String[] args) {
        SpringApplication.run(GsRiseApplication.class, args);
    }
}
