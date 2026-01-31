package com.vnote.VNote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class VNoteBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(VNoteBackendApplication.class, args);
    }
}
