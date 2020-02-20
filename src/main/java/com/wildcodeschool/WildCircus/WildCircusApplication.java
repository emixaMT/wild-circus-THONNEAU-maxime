package com.wildcodeschool.WildCircus;

import com.wildcodeschool.WildCircus.storage.StorageProperties;
import com.wildcodeschool.WildCircus.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class WildCircusApplication {

	public static void main(String[] args) {
		SpringApplication.run(WildCircusApplication.class, args);
	}


	@Bean
	CommandLineRunner init(StorageService storageService){
		return (args) -> {
			//storageService.deleteAll();
			//storageService.init();
		};
	}
}
