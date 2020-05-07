package org.dualstudents;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StarfleetApplication {

    public static void main(String[] args) {
        System.setProperty("inputFile", args[0]);
        System.setProperty("chunkSize", args[1]);
        SpringApplication.run(StarfleetApplication.class, args);
    }
}