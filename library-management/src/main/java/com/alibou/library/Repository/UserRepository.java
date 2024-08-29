package com.alibou.library.Repository;

import com.alibou.library.modal.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(@Email(message = "Email should be in a proper format") @NotBlank(message = "Email is mandatory") String email);
    Optional<User> findById(String id);
}
