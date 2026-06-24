package com.example.resourcesharingsystem.repository;

import com.example.resourcesharingsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findById(long id);

    boolean existsByEmail(String email);

    boolean existsByMobile(String mobile);

    User findByEmail(String email);

}
