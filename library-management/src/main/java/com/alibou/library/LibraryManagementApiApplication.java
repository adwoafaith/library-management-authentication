package com.alibou.library;

import com.alibou.library.Repository.RoleRepository;
import com.alibou.library.modal.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaRepositories // if we don't include it here the auditing in the entity will not work
@EnableAsync
public class LibraryManagementApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryManagementApiApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(RoleRepository roleRepository) {
		return args -> {
			if (!roleRepository.findByRole("USER").isPresent()) {
				Role userRole = Role.builder()
						.role("USER")
						.build();
				roleRepository.save(userRole);
			}
		};
	}
}