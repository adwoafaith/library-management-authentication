package com.alibou.library.Repository;

import com.alibou.library.modal.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {

    Optional<Role> findByRole(String name);
}
