package com.example.gameChanger.Player;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PlayerConfig {
    @Bean
    CommandLineRunner commandLineRunnerPlayer (PlayerRepository repository) { //Zugang zum Repository durch Injection
        return args -> {

        };
    }
}
